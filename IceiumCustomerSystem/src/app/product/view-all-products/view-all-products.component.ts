import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PrimeNGConfig, SelectItem } from 'primeng/api';
import { Product } from 'src/app/models/product';

import { ProductService } from 'src/app/services/product.service';

@Component({
    selector: 'app-view-all-products',
    templateUrl: './view-all-products.component.html',
    styleUrls: ['./view-all-products.component.css'],
})
export class ViewAllProductsComponent implements OnInit {
    products: Product[];
    sortOptions: SelectItem[];
    sortKey: string = '';
    sortField: string = '';
    sortOrder: number = 0;

    constructor(
        private productService: ProductService,
        private primengConfig: PrimeNGConfig,
        private activatedRoute: ActivatedRoute
    ) {
        this.products = new Array();
        this.sortOptions = [
            { label: 'Price High to Low', value: '!unitPrice' },
            { label: 'Price Low to High', value: 'unitPrice' },
        ];

        this.primengConfig.ripple = true;
    }

    ngOnInit(): void {
        this.activatedRoute.queryParams.subscribe((params) => {
            let keyword: string | undefined = params['keyword'];
            let categoryIds: number[] | undefined = params['category'];
            let brandIds: number[] | undefined = params['brand'];
            let priceMin: number | undefined = params['priceMin'];
            let priceMax: number | undefined = params['priceMax'];
            let colorIds: number[] | undefined = params['color'];
            let sizeIds: number[] | undefined = params['size'];

            if (categoryIds && !Array.isArray(categoryIds)) {
                categoryIds = new Array(categoryIds);
            }
            if (brandIds && !Array.isArray(brandIds)) {
                brandIds = new Array(brandIds);
            }
            if (colorIds && !Array.isArray(colorIds)) {
                colorIds = new Array(colorIds);
            }
            if (sizeIds && !Array.isArray(sizeIds)) {
                sizeIds = new Array(sizeIds);
            }

            this.fetchProducts(
                keyword,
                categoryIds,
                brandIds,
                priceMin,
                priceMax,
                colorIds,
                sizeIds
            );
        });
    }

    fetchProducts(
        keyword?: string,
        categoryIds?: number[],
        brandIds?: number[],
        priceMin?: number,
        priceMax?: number,
        colorIds?: number[],
        sizeIds?: number[]
    ) {
        this.productService.getProducts().subscribe(
            (response) => {
                this.products = response.filter(
                    (product) =>
                        !product.categoryEntity?.name?.includes('Customize') &&
                        !product.modelEntity?.brandEntity?.name?.includes(
                            'Iceium'
                        ) &&
                        !product.specificationEntities?.forEach((spec) => {
                            if (spec.quantityOnHand) {
                                return spec.quantityOnHand < 0;
                            } else {
                                return false;
                            }
                        })
                );
                this.products = this.products.filter((product) => {
                    let searchShow: boolean | undefined = true;
                    let categoryShow: boolean | undefined = true;
                    let brandShow: boolean | undefined = true;
                    let priceShow: boolean | undefined = true;
                    let colorShow: boolean | undefined = true;
                    let sizeShow: boolean | undefined = true;

                    if (keyword) {
                        searchShow = product.name
                            ?.toLowerCase()
                            .includes(keyword?.toLowerCase());
                    }

                    if (categoryIds) {
                        for (let categoryId of categoryIds) {
                            categoryShow =
                                product.categoryEntity?.categoryId ==
                                categoryId;
                            if (categoryShow) break;
                            if (product.categoryEntity?.parentCategoryEntity) {
                                categoryShow =
                                    product.categoryEntity.parentCategoryEntity
                                        .categoryId == categoryId;
                                if (categoryShow) break;
                            }
                        }
                    }

                    if (brandIds) {
                        for (let brandId of brandIds) {
                            brandShow =
                                product.modelEntity?.brandEntity?.brandId ==
                                brandId;
                            if (brandShow) break;
                        }
                    }

                    if (priceMin && priceMax && product.unitPrice) {
                        if (product.saleEntity?.discountRate) {
                            let discountedPrice =
                                product.unitPrice -
                                product.unitPrice *
                                    product.saleEntity.discountRate;
                            priceShow =
                                discountedPrice > priceMin &&
                                discountedPrice < priceMax;
                        } else {
                            priceShow =
                                product.unitPrice > priceMin &&
                                product.unitPrice < priceMax;
                        }
                    }

                    if (colorIds) {
                        for (let colorId of colorIds) {
                            if (product.specificationEntities) {
                                for (let specification of product.specificationEntities) {
                                    colorShow =
                                        colorId ==
                                        specification.colorEntity?.colorId;
                                    if (colorShow) break;
                                }
                            }
                            if (colorShow) break;
                        }
                    }

                    if (sizeIds) {
                        for (let sizeId of sizeIds) {
                            if (product.specificationEntities) {
                                for (let specification of product.specificationEntities) {
                                    sizeShow =
                                        sizeId ==
                                        specification.sizeEntity?.sizeId;
                                    if (sizeShow) break;
                                }
                            }
                            if (sizeShow) break;
                        }
                    }

                    if (
                        searchShow &&
                        categoryShow &&
                        brandShow &&
                        priceShow &&
                        colorShow &&
                        sizeShow
                    ) {
                        return true;
                    } else {
                        return false;
                    }
                });

                this.products.forEach((product) => {
                    let totalRating = 0;
                    let totalOrder = 0;
                    product.specificationEntities?.forEach((specification) => {
                        specification.orderItemEntities?.forEach(
                            (orderItem) => {
                                totalOrder += 1;
                                if (
                                    orderItem != undefined &&
                                    orderItem.reviewEntity != undefined &&
                                    orderItem.reviewEntity.rating != undefined
                                ) {
                                    totalRating +=
                                        orderItem.reviewEntity.rating;
                                }
                            }
                        );
                    });
                    const rating =
                        totalOrder > 0 ? totalRating / totalOrder : 0;
                    product.rating =
                        Math.round((rating + Number.EPSILON) * 100) / 100;
                });
            },
            (error) => {
                console.log('********** ViewAllProductsComponent.ts: ' + error);
            }
        );
    }
    onSortChange(event: any) {
        let value = event.value;

        if (value.indexOf('!') === 0) {
            this.sortOrder = -1;
            this.sortField = value.substring(1, value.length);
        } else {
            this.sortOrder = 1;
            this.sortField = value;
        }
    }
}
