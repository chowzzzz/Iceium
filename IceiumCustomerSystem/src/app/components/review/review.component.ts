import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Address } from 'src/app/models/address';
import { CreditCard } from 'src/app/models/credit-card';
import { PaymentMethodEnum } from 'src/app/models/enum/payment-method-enum.enum';
import { Order } from 'src/app/models/order';
import { OrderItem } from 'src/app/models/order-item';
import { Coupon } from 'src/app/models/coupon';
import { OrderService } from 'src/app/services/order.service';
import { ProductService } from 'src/app/services/product.service';
import { SessionService } from 'src/app/services/session.service';
import { SpecificationService } from 'src/app/services/specification.service';
import { MessageService } from 'primeng/api';
import { AddressService } from 'src/app/services/address.service';
import { CustomerService } from 'src/app/services/customer.service';
import { Customer } from 'src/app/models/customer';

@Component({
    selector: 'app-review',
    templateUrl: './review.component.html',
    styleUrls: ['./review.component.css'],
    providers: [MessageService],
})
export class ReviewComponent implements OnInit {
    cart: OrderItem[];
    coupon: Coupon | undefined;
    total: number;
    paymentMethod: String;
    deliveryAddress: Address;
    creditCard: CreditCard;
    submitted: boolean;
    currCustomer: Customer;

    constructor(
        private router: Router,
        private sessionService: SessionService,
        private messageService: MessageService,
        private specificationService: SpecificationService,
        private productService: ProductService,
        private orderService: OrderService,
        private addressService: AddressService,
        private customerService: CustomerService
    ) {
        this.cart = [];
        this.total = 0;
        this.paymentMethod = '';
        this.deliveryAddress = new Address();
        this.creditCard = new CreditCard();
        this.submitted = false;
        this.coupon = undefined;
        this.currCustomer = this.sessionService.getCurrentCustomer();
    }

    ngOnInit(): void {
        let tmpCart = this.sessionService.getCart();
        let tmpCoupon = this.sessionService.getCoupon();
        let tmpDeliveryAddress = this.sessionService.getDeliveryAddress();
        let tmpPaymentMethod = this.sessionService.getPaymentMethod();
        let tmpCreditCard = this.sessionService.getCreditCard();

        if (tmpCart) {
            this.cart = tmpCart;
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

        if (tmpCoupon) {
            this.coupon = tmpCoupon;
        }

        if (tmpDeliveryAddress) {
            this.deliveryAddress = tmpDeliveryAddress;
        }

        if (tmpPaymentMethod) {
            this.paymentMethod =
                tmpPaymentMethod === PaymentMethodEnum.CREDIT_CARD
                    ? 'Credit Card'
                    : 'Cash on Delivery';
        }

        if (tmpCreditCard && tmpCreditCard.creditCardNumber != undefined) {
            this.creditCard = tmpCreditCard;
            if (typeof this.creditCard.expiryDate === 'string') {
                this.creditCard.expiryDate = new Date(
                    this.creditCard.expiryDate
                );
            }
        }
    }

    prevPage(): void {
        this.router.navigate(['checkout/paymentMethod']);
    }

    getDiscountedUnitPrice(orderItem: OrderItem): number {
        if (
            orderItem.orderItemNumber &&
            orderItem.productEntity?.unitPrice &&
            orderItem.productEntity?.saleEntity &&
            orderItem.productEntity?.saleEntity?.discountRate
        ) {
            let discountRate =
                orderItem.productEntity?.saleEntity?.discountRate;
            let unitPrice = orderItem.productEntity?.unitPrice;
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
            orderItem.productEntity?.saleEntity?.discountRate
        ) {
            let discountRate =
                orderItem.productEntity?.saleEntity?.discountRate;
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

    checkout(): void {
        this.submitted = true;

        let totalOrderItem = 0;
        let totalQuantity = 0;
        let totalAmount = 0.0;
        let paymentMethodEnum =
            this.paymentMethod === 'Credit Card'
                ? PaymentMethodEnum.CREDIT_CARD
                : PaymentMethodEnum.CASH_ON_DELIVERY;
        let order = new Order();

        this.cart.forEach((orderItem) => {
            if (
                orderItem &&
                orderItem.quantity &&
                orderItem.subTotal &&
                orderItem.specificationId
            ) {
                totalOrderItem++;
                totalQuantity += orderItem.quantity;
                order.addOrderItem(orderItem);
            }
        });

        order.totalOrderItem = totalOrderItem;
        order.totalQuantity = totalQuantity;
        order.totalAmount = totalAmount;
        order.paymentMethodEnum = paymentMethodEnum;

        let addressId;

        if (this.deliveryAddress.address && this.deliveryAddress.postalCode) {
            this.addressService
                .getAddressByAddressAndPostalCode(
                    this.deliveryAddress.address,
                    this.deliveryAddress.postalCode
                )
                .subscribe(
                    (address) => {
                        addressId = address.addressId;
                        let couponId = this.coupon?.promotionId;
                        if (addressId) {
                            this.orderService
                                .createOrder(order, addressId, couponId)
                                .subscribe(
                                    (orderId) => {
                                        this.sessionService.setCheckoutOrderId(
                                            orderId
                                        );
                                        this.sessionService.clearCheckout();

                                        this.router.navigate([
                                            '/checkoutConfirmation',
                                            { orderId: orderId },
                                        ]);
                                    },
                                    (error) => {
                                        this.messageService.add({
                                            severity: 'error',
                                            summary: 'Error',
                                            detail:
                                                'An error has occurred while creating the new order: ' +
                                                error,
                                        });
                                        console.log(error);
                                    }
                                );
                        }
                    },
                    (error) => {
                        if (error.status === 400) {
                            this.addressService
                                .createAddress(this.deliveryAddress)
                                .subscribe(
                                    (addressId) => {
                                        let couponId = this.coupon?.promotionId;
                                        if (this.currCustomer.customerId) {
                                            this.customerService
                                                .getCustomerByCustomerId(
                                                    this.currCustomer.customerId
                                                )
                                                .subscribe(
                                                    (response) => {
                                                        this.currCustomer = response;
                                                        this.sessionService.setCurrentCustomer(
                                                            this.currCustomer
                                                        );
                                                        if (addressId) {
                                                            this.orderService
                                                                .createOrder(
                                                                    order,
                                                                    addressId,
                                                                    couponId
                                                                )
                                                                .subscribe(
                                                                    (
                                                                        orderId
                                                                    ) => {
                                                                        console.log(
                                                                            orderId
                                                                        );

                                                                        this.sessionService.setCart(
                                                                            new Array()
                                                                        );

                                                                        this.router.navigate(
                                                                            [
                                                                                '/checkoutConfirmation',
                                                                                {
                                                                                    orderId: orderId,
                                                                                },
                                                                            ]
                                                                        );
                                                                    },
                                                                    (error) => {
                                                                        this.messageService.add(
                                                                            {
                                                                                severity:
                                                                                    'error',
                                                                                summary:
                                                                                    'Error',
                                                                                detail:
                                                                                    'An error has occurred while creating the new order: ' +
                                                                                    error,
                                                                            }
                                                                        );
                                                                        console.log(
                                                                            error
                                                                        );
                                                                    }
                                                                );
                                                        }
                                                    },
                                                    (error) => {
                                                        this.messageService.add(
                                                            {
                                                                severity:
                                                                    'error',
                                                                summary:
                                                                    'Error',
                                                                detail:
                                                                    'An error has occurred while adding a new address: ' +
                                                                    error,
                                                            }
                                                        );
                                                    }
                                                );
                                        }
                                    },
                                    (error) => {
                                        this.messageService.add({
                                            severity: 'error',
                                            summary: 'Error',
                                            detail:
                                                'An error has occurred while adding a new address: ' +
                                                error,
                                        });
                                    }
                                );
                        } else {
                            let errorMessage: string = '';
                            if (error.error instanceof ErrorEvent) {
                                errorMessage =
                                    'An unknown error has occurred: ' +
                                    error.error;
                            } else {
                                errorMessage =
                                    'A HTTP error has occurred: ' +
                                    `HTTP ${error.status}: ${error.error}`;
                            }

                            this.messageService.add({
                                severity: 'error',
                                summary: 'Error',
                                detail:
                                    'An error has occurred while updating address: ' +
                                    errorMessage,
                                life: 3000,
                            });
                        }
                    }
                );
        }
    }
}
