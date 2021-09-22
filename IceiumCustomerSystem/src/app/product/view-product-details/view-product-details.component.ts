import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { MessageService } from 'primeng/api';

import { SessionService } from '../../services/session.service';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { Specification } from '../../models/specification';
import { Size } from '../../models/size';
import { Color } from '../../models/color';
import { OrderItem } from '../../models/order-item';
import { Review } from 'src/app/models/review';

@Component({
    selector: 'app-view-product-details',
    templateUrl: './view-product-details.component.html',
    styleUrls: ['./view-product-details.component.css'],
    providers: [MessageService],
})
export class ViewProductDetailsComponent implements OnInit {
    productId: string | null;
    productToView: Product;
    reviewsToView: Review[];
    sizesAvailable: Size[];
    colorsAvailable: Color[];
    retrieveProductError: boolean;

    selectedSize: Size | undefined;
    selectedColor: Color | undefined;
    haveSize: boolean = true;

    resultSuccess: boolean;
    resultError: boolean;

    constructor(
        private router: Router,
        private activatedRoute: ActivatedRoute,
        public sessionService: SessionService,
        private messageService: MessageService,
        private productService: ProductService
    ) {
        this.productId = null;
        this.productToView = new Product();
        this.reviewsToView = new Array();
        this.sizesAvailable = new Array();
        this.colorsAvailable = new Array();

        this.retrieveProductError = false;

        this.resultSuccess = false;
        this.resultError = false;
    }

    ngOnInit(): void {
        this.productId = this.activatedRoute.snapshot.paramMap.get('productId');
        this.haveSize = true;

        if (this.productId != null) {
            this.productService.getProduct(parseInt(this.productId)).subscribe(
                (response) => {
                    this.productToView = response;

                    let totalRating = 0;
                    let totalOrder = 0;
                    this.productToView.specificationEntities?.forEach(
                        (specification: Specification) => {
                            let add = true;
                            this.sizesAvailable?.forEach(
                                (size: Size | undefined) => {
                                    if (
                                        size != undefined &&
                                        size.size ===
                                            specification.sizeEntity?.size
                                    ) {
                                        add = false;
                                        return;
                                    }
                                }
                            );

                            if (specification.sizeEntity && add) {
                                this.sizesAvailable.push(
                                    specification.sizeEntity
                                );
                            }

                            add = true;
                            this.colorsAvailable?.forEach(
                                (color: Color | undefined) => {
                                    if (
                                        color != undefined &&
                                        color.hex ===
                                            specification.colorEntity?.hex
                                    ) {
                                        add = false;
                                        return;
                                    }
                                }
                            );

                            if (specification.colorEntity && add) {
                                this.colorsAvailable.push(
                                    specification.colorEntity
                                );
                            }

                            specification.orderItemEntities?.forEach(
                                (orderItem) => {
                                    totalOrder += 1;
                                    if (
                                        orderItem != undefined &&
                                        orderItem.reviewEntity != undefined &&
                                        orderItem.reviewEntity.rating !=
                                            undefined
                                    ) {
                                        this.reviewsToView.push(orderItem.reviewEntity);
                                        totalRating +=
                                            orderItem.reviewEntity.rating;
                                    }
                                }
                            );
                        }
                    );

                    const rating =
                        totalOrder > 0 ? totalRating / totalOrder : 0;
                    this.productToView.rating =
                        Math.round((rating + Number.EPSILON) * 100) / 100;

                    this.sizesAvailable.sort((s1, s2) => {
                        if (
                            s1 != undefined &&
                            s2 != undefined &&
                            s1.size != undefined &&
                            s2.size != undefined
                        ) {
                            return s1.size - s2.size;
                        } else {
                            return 0;
                        }
                    });

                    this.selectedSize = this.sizesAvailable[0];
                    this.selectedColor = this.colorsAvailable[0];
                    if (
                        this.sizesAvailable[0]?.sizeTypeEnum?.valueOf() ==
                        'NONE'
                    ) {
                        this.haveSize = false;
                    }
                },
                (error) => {
                    this.retrieveProductError = true;
                    console.log(
                        '********** ViewProductDetailsComponent.ts: ' + error
                    );
                }
            );
        }
    }

    addToCart(addToCartForm: NgForm) {
        if (addToCartForm.valid) {
            let selectedSpecification: Specification | undefined;
            this.productToView.specificationEntities?.forEach(
                (specification: Specification | undefined) => {
                    if (
                        specification != undefined &&
                        specification.colorEntity != null &&
                        specification.sizeEntity != null &&
                        specification.colorEntity.colorId ==
                            this.selectedColor?.colorId &&
                        specification.sizeEntity.sizeId ==
                            this.selectedSize?.sizeId
                    ) {
                        selectedSpecification = specification;
                    }
                }
            );

            let cart: OrderItem[] | undefined = this.sessionService.getCart();

            let newProductOrderItem = true;

            cart?.forEach((item) => {
                if (item.productId === this.productToView.productId) {
                    newProductOrderItem = false;
                    if (item.quantity) {
                        item.quantity += 1;
                    } else {
                        item.quantity = 1;
                    }
                    if (item.subTotal && this.productToView.unitPrice) {
                        item.subTotal += this.productToView.unitPrice;
                    } else {
                        item.subTotal = this.productToView.unitPrice;
                    }
                }
            });

            if (newProductOrderItem) {
                let orderItemNumber;
                if (cart) {
                    orderItemNumber = cart.length + 1;
                } else {
                    orderItemNumber = 1;
                }

                const orderItem: OrderItem = new OrderItem(
                    orderItemNumber,
                    1,
                    this.productToView.unitPrice
                );

                orderItem.productId = this.productToView.productId;
                orderItem.specificationId =
                    selectedSpecification?.specificationId;
                orderItem.specificationEntity = selectedSpecification;

                if (cart) {
                    cart.push(orderItem);
                } else {
                    cart = [orderItem];
                }
            }

            this.sessionService.setCart(cart!);

            this.resultSuccess = true;
            this.resultError = false;
            this.messageService.add({
                severity: 'success',
                summary:
                    'Product ' + this.productToView.name + ' added to cart',
            });
        } else {
            this.resultError = true;
            this.resultSuccess = false;
            this.messageService.add({
                severity: 'error',
                summary:
                    'An error has occurred while adding to cart: Cart form is invalid',
            });

            console.log('********** ViewProductDetailsComponent.ts: ');
        }
    }

    getDiscountedUnitPrice(productToView: Product): number {
        if (productToView.unitPrice && productToView.saleEntity?.discountRate) {
            return (
                productToView.unitPrice -
                productToView.unitPrice * productToView.saleEntity?.discountRate
            );
        } else {
            return 0;
        }
    }
}
