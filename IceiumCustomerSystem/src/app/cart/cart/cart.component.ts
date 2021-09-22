import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';

import { SessionService } from '../../services/session.service';
import { OrderItem } from '../../models/order-item';
import { Product } from '../../models/product';
import { ProductService } from 'src/app/services/product.service';
import { CouponService } from 'src/app/services/coupon.service';
import { SpecificationService } from 'src/app/services/specification.service';
import { Coupon } from 'src/app/models/coupon';

@Component({
    selector: 'app-cart',
    templateUrl: './cart.component.html',
    styleUrls: ['./cart.component.css'],
    providers: [MessageService, ConfirmationService],
})
export class CartComponent implements OnInit {
    cart: OrderItem[];
    couponCode: string;
    coupon: Coupon | undefined;
    total: number;

    constructor(
        private router: Router,
        private activatedRoute: ActivatedRoute,
        public sessionService: SessionService,
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private productService: ProductService,
        private specificationService: SpecificationService,
        private couponService: CouponService
    ) {
        this.cart = new Array();
        this.couponCode = '';
        this.coupon = undefined;
        this.total = 0.0;
    }

    ngOnInit(): void {
        const tempCart = this.sessionService.getCart();
        const tempCoupon = this.sessionService.getCoupon();
        if (tempCart) {
            this.cart = tempCart;
            this.cart.forEach((orderItem) => {
                if (
                    orderItem &&
                    orderItem.specificationId &&
                    orderItem.productId
                ) {
                    this.specificationService
                        .getSpecification(orderItem.specificationId)
                        .subscribe(
                            (response) => {
                                orderItem.specificationEntity = response;
                            },
                            (error) => {
                                console.log(
                                    '********** RetrieveProductBySpecificationId.ts: ' +
                                        error
                                );
                            }
                        );

                    this.productService
                        .getProduct(orderItem.productId)
                        .subscribe(
                            (response) => {
                                orderItem.productEntity = response;

                                if (orderItem.subTotal) {
                                    if (
                                        orderItem.orderItemNumber &&
                                        orderItem.productEntity.saleEntity
                                    ) {
                                        this.total += this.getDiscountedSubtotal(
                                            orderItem
                                        );
                                    } else {
                                        this.total += orderItem.subTotal;
                                    }
                                }
                            },
                            (error) => {
                                console.log(
                                    '********** RetrieveProductBySpecificationId.ts: ' +
                                        error
                                );
                            }
                        );
                }
            });
        }

        if (tempCoupon) {
            this.coupon = tempCoupon;
        }
    }

    getDiscountedUnitPrice(orderItem: OrderItem): number {
        if (
            orderItem.orderItemNumber &&
            orderItem.productEntity?.unitPrice &&
            orderItem.productEntity.saleEntity &&
            orderItem.productEntity.saleEntity?.discountRate
        ) {
            let discountRate = orderItem.productEntity.saleEntity?.discountRate;
            let unitPrice = orderItem.productEntity.unitPrice;
            if (discountRate && unitPrice) {
                return unitPrice - unitPrice * discountRate;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    getDiscountedSubtotal(orderItem: OrderItem): number {
        if (
            orderItem.subTotal &&
            orderItem.orderItemNumber &&
            orderItem.productEntity?.saleEntity &&
            orderItem.productEntity.saleEntity?.discountRate
        ) {
            let discountRate = orderItem.productEntity.saleEntity?.discountRate;
            if (discountRate) {
                return orderItem.subTotal - orderItem.subTotal * discountRate;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    getDiscountedTotal(total: number): number {
        if (this.coupon && this.coupon.discountRate) {
            return total - total * this.coupon.discountRate;
        } else {
            return 0;
        }
    }

    applyPromotionCode(applyPromotionCodeForm: NgForm): void {
        if (applyPromotionCodeForm.valid) {
            if (this.couponCode.length == 0) {
                this.messageService.add({
                    severity: 'warn',
                    summary: 'Invalid',
                    detail: 'Please enter a Coupon Code',
                });
            } else {
                this.couponService
                    .retrieveCouponByCouponCode(this.couponCode)
                    .subscribe(
                        (response) => {
                            let coupon = response;

                            if (coupon && coupon.minimumSpend) {
                                if (this.total >= coupon.minimumSpend) {
                                    let currentRedemptions =
                                        coupon.currentRedemptions;
                                    let maximumRedemptions =
                                        coupon.maximumRedemptions;
                                    if (
                                        currentRedemptions &&
                                        maximumRedemptions &&
                                        currentRedemptions < maximumRedemptions
                                    ) {
                                        this.couponCode = '';
                                        this.coupon = coupon;
                                        this.sessionService.setCoupon(
                                            this.coupon
                                        );
                                        this.messageService.add({
                                            severity: 'success',
                                            summary: 'Success',
                                            detail: 'Coupon Code is valid',
                                        });
                                    } else {
                                        this.messageService.add({
                                            severity: 'warn',
                                            summary: 'Warn',
                                            detail:
                                                'This coupon has been fully redeemed',
                                        });
                                    }
                                } else {
                                    var formatter = new Intl.NumberFormat(
                                        'en-SG',
                                        { style: 'currency', currency: 'SGD' }
                                    );
                                    this.messageService.add({
                                        severity: 'warn',
                                        summary: 'Warn',
                                        detail:
                                            'Total amount does not meet the minimum spend of ' +
                                            formatter.format(
                                                coupon.minimumSpend
                                            ),
                                    });
                                }
                            }
                        },
                        (error) => {
                            this.messageService.add({
                                severity: 'info',
                                summary: 'Invalid',
                                detail: 'Coupon Code is invalid',
                            });
                            console.log(
                                '********** CheckCouponExistByCouponCode.ts: ' +
                                    error
                            );
                        }
                    );
            }
        }
    }

    private removeFromCart(subtotal: number, orderItemNumber: number): void {
        let i = 1;
        this.total -= subtotal;

        let cartItemIndex = this.cart.findIndex((oi) => {
            console.log(oi);
            return oi.orderItemNumber === orderItemNumber;
        });

        this.messageService.add({
            severity: 'info',
            summary: 'Product removed',
            detail:
                this.cart[cartItemIndex].productEntity?.name +
                ' has been removed',
        });
        this.cart.splice(cartItemIndex, 1);

        this.sessionService.setCart(this.cart);

        if (
            this.coupon &&
            this.coupon.minimumSpend &&
            this.coupon.minimumSpend > this.total
        ) {
            this.coupon = undefined;
        }
    }

    cartChangeQuantity(event: any, orderItem: OrderItem): void {
        if (orderItem && orderItem.orderItemNumber) {
            let quantity = orderItem.quantity;
            let prevSubtotal = orderItem.subTotal;
            let unitPrice = orderItem.productEntity?.unitPrice;
            if (orderItem.productEntity?.saleEntity) {
                prevSubtotal = this.getDiscountedSubtotal(orderItem);
            }
            if (this.cart && prevSubtotal && quantity == 0) {
                this.removeFromCart(prevSubtotal, orderItem.orderItemNumber);
            } else {
                if (prevSubtotal && quantity && unitPrice) {
                    let newSubtotal = quantity * unitPrice;
                    let cartItemIndex = this.cart.findIndex((oi) => {
                        return oi.orderItemNumber === orderItem.orderItemNumber;
                    });
                    this.cart[cartItemIndex].subTotal = newSubtotal;
                    this.cart[cartItemIndex].quantity = quantity;
                    let subTotalToAdd = newSubtotal;
                    if (orderItem.productEntity?.saleEntity) {
                        subTotalToAdd = this.getDiscountedSubtotal(orderItem);
                    }
                    this.total -= prevSubtotal;
                    this.total += subTotalToAdd;
                }
            }
            this.sessionService.setCart(this.cart);
        }
    }

    deleteOrderItem(orderItem: OrderItem) {
        if (orderItem && orderItem.orderItemNumber) {
            this.confirmationService.confirm({
                message:
                    'Are you sure you want to delete ' +
                    orderItem.productEntity?.name +
                    '?',
                header: 'Confirm',
                icon: 'pi pi-exclamation-triangle',
                accept: () => {
                    if (orderItem.subTotal && orderItem.orderItemNumber) {
                        if (orderItem.productEntity?.saleEntity) {
                            this.removeFromCart(
                                this.getDiscountedSubtotal(orderItem),
                                orderItem.orderItemNumber
                            );
                        } else {
                            this.removeFromCart(
                                orderItem.subTotal,
                                orderItem.orderItemNumber
                            );
                        }
                    }
                },
            });
        }
    }

    checkoutCart(): void {
        if (this.sessionService.getIsLogin()) {
            this.sessionService.setCart(this.cart);
            this.router.navigate(['/checkout']);
        } else {
            this.messageService.add({
                severity: 'info',
                summary: 'Hi!',
                detail: 'Please Login before checking out',
            });
        }
    }
}
