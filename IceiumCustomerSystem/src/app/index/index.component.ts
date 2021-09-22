import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Brand } from '../models/brand';
import { Category } from '../models/category';
import { Product } from '../models/product';
import { BrandService } from '../services/brand.service';
import { CategoryService } from '../services/category.service';
import { ProductService } from '../services/product.service';

@Component({
    selector: 'app-index',
    templateUrl: './index.component.html',
    styleUrls: ['./index.component.css'],
})
export class IndexComponent implements OnInit {
    categories: Category[];
    brands: Brand[];
    products: Product[];
    newProducts: Product[];
    newProduct: string = 'New Arrivals';
    hotProduct: string = '🔥 Hot Products 🔥';
    hotProducts: Product[];

    constructor(
        private categoryService: CategoryService,
        private brandService: BrandService,
        private productService: ProductService,
        private router: Router
    ) {
        this.categories = new Array();
        this.brands = new Array();
        this.products = new Array();
        this.newProducts = new Array();
        this.hotProducts = new Array();
    }

    ngOnInit(): void {
        this.categoryService.getCategories().subscribe(
            (res) => {
                this.categories = res.filter(
                    (category) => !category.name?.includes('Customize')
                );
            },
            (error) => {
                console.log(`Error getting categories ${error}`);
            }
        );

        this.brandService.getBrands().subscribe(
            (res) => {
                this.brands = res.filter(
                    (brand) => !brand.name?.includes('Iceium')
                );
            },
            (error) => {
                console.log(`Error getting brands ${error}`);
            }
        );

        this.productService.getProducts().subscribe(
            (res) => {
                this.products = res.filter(
                    (product) =>
                        !product.categoryEntity?.name?.includes('Customize') &&
                        !product.modelEntity?.brandEntity?.name?.includes(
                            'Iceium'
                        )
                );
                this.newProducts = this.products;
                this.hotProducts = this.products;

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

                this.newProducts = new Array();
                this.hotProducts = new Array();

                this.products.forEach((product) => {
                    product.tagEntities?.forEach((tag) => {
                        if (tag.name == 'New') {
                            this.newProducts.push(product);
                        }
                    });
                });
                this.products.forEach((product) => {
                    product.tagEntities?.forEach((tag) => {
                        if (tag.name == 'Hot') {
                            this.hotProducts.push(product);
                        }
                    });
                });
                // console.log(this.newProducts);
                // console.log(this.hotProducts);
            },
            (error) => {
                console.log(`Error getting products ${error}`);
            }
        );
    }

    viewProductsCategory(category: string) {
        this.router.navigate(['/viewAllProducts'], {
            queryParams: { category: category },
        });
    }
    viewProductsBrand(brand: string) {
        this.router.navigate(['/viewAllProducts'], {
            queryParams: { brand: brand },
        });
    }
}
