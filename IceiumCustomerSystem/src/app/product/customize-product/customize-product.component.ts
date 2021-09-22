import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MessageService, SelectItem } from 'primeng/api';
import { Color } from 'src/app/models/color';
import { Model } from 'src/app/models/model';
import { OrderItem } from 'src/app/models/order-item';
import { Product } from 'src/app/models/product';
import { Size } from 'src/app/models/size';
import { Specification } from 'src/app/models/specification';
import { ColorService } from 'src/app/services/color.service';
import { ModelService } from 'src/app/services/model.service';
import { ProductService } from 'src/app/services/product.service';
import { SessionService } from 'src/app/services/session.service';
import { SizeService } from 'src/app/services/size.service';

@Component({
    selector: 'app-customize-product',
    templateUrl: './customize-product.component.html',
    styleUrls: ['./customize-product.component.css'],
    providers: [MessageService],
})
export class CustomizeProductComponent implements OnInit {
    skateTypes: Model[] | undefined;
    selectedSkateType: Model | undefined;
    show: boolean = true;
    submitted: boolean = false;

    colors: Color[];
    figureSkateColor: string = '#fff';
    hockeySkateColor: string = '#fff';
    figureShoelaceColor: string = '#fff';
    hockeyShoelaceColor: string = '#fff';
    hockeySkate2Color: string = '#fff';
    selectedFigureSkateColor: Color | undefined;
    selectedHockeySkateColor: Color | undefined;
    colorError: boolean = false;

    sizes: Size[];
    selectedSize: Size | undefined;
    sortedSizes: Size[];

    newProduct: Product;
    newSpecification: Specification;

    resultSuccess: boolean;
    resultError: boolean;

    constructor(
        private sizeService: SizeService,
        private modelService: ModelService,
        private productService: ProductService,
        private colorService: ColorService,
        public sessionService: SessionService,
        private messageService: MessageService
    ) {
        this.colors = new Array();
        this.sizes = new Array();
        this.sortedSizes = new Array();
        this.newProduct = new Product();
        this.newSpecification = new Specification();

        this.resultSuccess = false;
        this.resultError = false;
    }

    ngOnInit(): void {
        this.fetchSizes();
        this.fetchModels();
        this.fetchColors();
    }

    onSelectSkateType(event: any) {
        if (event.value.name == 'Hockey Skates') {
            this.show = false;
        } else {
            this.show = true;
        }
    }

    changeFigureSkateColor(newColor: Color) {
        this.figureSkateColor = newColor.hex!;
        this.selectedFigureSkateColor = newColor;
    }

    // changeFigureShoelaceColor(newColor: string) {
    //     this.figureShoelaceColor = newColor;
    // }

    changeHockeySkateColor(newColor: Color) {
        this.hockeySkateColor = newColor.hex!;
        this.selectedHockeySkateColor = newColor;
    }

    // changeHockeySkate2Color(newColor: string) {
    //     console.log(newColor);
    //     this.hockeySkate2Color = newColor;
    // }

    // changeHockeyShoelaceColor(newColor: string) {
    //     this.hockeyShoelaceColor = newColor;
    // }

    fetchModels() {
        this.modelService.getModels().subscribe((res) => {
            this.skateTypes = res.filter((model) => {
                return (
                    model.brandEntity?.name?.includes('Iceium') &&
                    !model.name?.includes('N/A')
                );
            });

            this.selectedSkateType = this.skateTypes[0];
        });
    }

    fetchSizes() {
        this.sizeService.getSizes().subscribe((res) => {
            this.sizes = res;
            this.sizes.forEach((size) => {
                if (size.sizeTypeEnum?.valueOf() == 'YOUTH') {
                    size.sizeYString = size.size?.toString() + ' Y';
                    size.sizeString = size.sizeYString;
                } else if (size.sizeTypeEnum?.valueOf() == 'NONE') {
                } else {
                    size.sizeNString = size.size?.toString();
                    size.sizeString = size.sizeNString;
                }
            });

            this.sizes.sort();
            this.sizes.forEach((size) => {
                if (size.sizeYString) {
                    this.sortedSizes.push(size);
                }
            });
            this.sizes.forEach((size) => {
                if (size.sizeNString) {
                    this.sortedSizes.push(size);
                }
            });
        });
    }

    fetchColors() {
        this.colorService.getColors().subscribe((res) => {
            this.colors = res;
        });
    }

    addToCart(createCustomizeProductForm: NgForm) {
        this.submitted = true;
        if (!this.selectedFigureSkateColor) {
            this.colorError = true;
        } else {
            this.colorError = false;
            // if figure skates are selected
            if (createCustomizeProductForm.valid) {
                let selectedColor: Color | undefined;
                if (this.show) {
                    this.newProduct.name = 'Customized Figure Skates';
                    this.newProduct.description = 'Customized Figure Skates';
                    this.newProduct.imageLink =
                        'iceium_figureskates_nolabel.jpg';
                    selectedColor = this.selectedFigureSkateColor;
                } else {
                    this.newProduct.name = 'Customized Hockey Skates';
                    this.newProduct.description = 'Customized Hockey Skates';
                    this.newProduct.imageLink = 'iceium_hockeyskates_bauer.jpg';
                    selectedColor = this.selectedHockeySkateColor;
                }

                this.newSpecification.quantityOnHand = 1;
                this.newSpecification.reorderQuantity = 0;
                this.newProduct.unitPrice = 500;
                this.newProduct.skuCode = '';

                this.productService
                    .createCustomizeProduct(
                        this.newProduct,
                        this.selectedSkateType?.modelId,
                        this.newSpecification,
                        selectedColor,
                        this.selectedSize
                    )
                    .subscribe((newCustomizedProduct) => {
                        console.log(newCustomizedProduct);

                        let selectedSpecification: Specification | undefined;
                        newCustomizedProduct.specificationEntities?.forEach(
                            (specification: Specification | undefined) => {
                                if (
                                    specification &&
                                    specification.colorEntity &&
                                    specification.sizeEntity &&
                                    specification.colorEntity.colorId ==
                                        selectedColor?.colorId &&
                                    specification.sizeEntity.sizeId ==
                                        this.selectedSize?.sizeId
                                ) {
                                    selectedSpecification = specification;
                                }
                            }
                        );

                        let cart = this.sessionService.getCart();
                        let orderItemNumber;
                        if (cart) {
                            orderItemNumber = cart.length + 1;
                        } else {
                            orderItemNumber = 1;
                        }

                        const orderItem: OrderItem = new OrderItem(
                            orderItemNumber,
                            1,
                            newCustomizedProduct.unitPrice
                        );

                        orderItem.productId = newCustomizedProduct.productId;

                        orderItem.specificationId =
                            selectedSpecification?.specificationId;

                        if (cart) {
                            cart.push(orderItem);
                        } else {
                            cart = [orderItem];
                        }

                        this.sessionService.setCart(cart);

                        this.resultSuccess = true;
                        this.resultError = false;
                        this.messageService.add({
                            severity: 'success',
                            summary:
                                'Product ' +
                                newCustomizedProduct.name +
                                ' added to cart',
                        });
                    });
            } else {
                this.resultError = true;
                this.resultSuccess = false;
                this.messageService.add({
                    severity: 'error',
                    summary:
                        'An error has occurred while adding to cart: Cart form is invalid',
                });

                console.log('********** CustomizeProductComponent.ts: ');
            }
        }
    }
}
