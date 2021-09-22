import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Brand } from 'src/app/models/brand';
import { Category } from 'src/app/models/category';
import { Color } from 'src/app/models/color';
import { SizeTypeEnum } from 'src/app/models/enum/size-type-enum.enum';
import { Size } from 'src/app/models/size';
import { BrandService } from 'src/app/services/brand.service';
import { CategoryService } from 'src/app/services/category.service';
import { ColorService } from 'src/app/services/color.service';
import { SizeService } from 'src/app/services/size.service';

@Component({
    selector: 'app-filter-bar',
    templateUrl: './filter-bar.component.html',
    styleUrls: ['./filter-bar.component.css'],
})
export class FilterBarComponent implements OnInit {
    categories: Category[];
    selectedCategories: Category[] = new Array();
    selectedPriceRange: number[];
    brands: Brand[];
    selectedBrands: Brand[] = new Array();
    colors: Color[];
    selectedColors: Color[] = new Array();
    sizes: Size[];
    sortedSizes: Size[];
    selectedSizes: Size[] = new Array();

    constructor(
        private categoryService: CategoryService,
        private brandService: BrandService,
        private colorService: ColorService,
        private sizeService: SizeService,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) {
        this.categories = new Array();
        this.brands = new Array();
        this.selectedPriceRange = [0, 1500];
        this.colors = new Array();
        this.sizes = new Array();
        this.sortedSizes = new Array();
    }

    ngOnInit(): void {
        this.fetchCategories();
        this.setPriceRange();
        this.fetchBrands();
        this.fetchColors();
        this.fetchSizes();
    }

    fetchCategories() {
        this.categoryService.getCategories().subscribe(
            (res) => {
                this.categories = res.filter(
                    (category) => !category.name?.includes('Customize')
                );
                this.activatedRoute.queryParams.subscribe((params) => {
                    this.selectedCategories = new Array();
                    let urlCategoryIds: number[] | undefined =
                        params['category'];

                    if (
                        urlCategoryIds != undefined &&
                        !Array.isArray(urlCategoryIds)
                    ) {
                        urlCategoryIds = new Array(urlCategoryIds);
                    }
                    urlCategoryIds?.forEach((categoryId) => {
                        const temp: Category[] = this.categories.filter(
                            (cat) => cat.categoryId == +categoryId
                        );
                        this.selectedCategories = this.selectedCategories.concat(
                            temp
                        );
                    });
                });
            },
            (error) => {
                console.log('********** ViewAllProductsComponent.ts: ' + error);
            }
        );
    }

    onCategoryChange(event: any, category?: Category) {
        // console.log(this.selectedCategories);
        let extractedCategoryIds = this.selectedCategories.map(
            (a) => a.categoryId
        );
        this.router.navigate(['/viewAllProducts'], {
            queryParams: {
                category: extractedCategoryIds,
            },
            queryParamsHandling: 'merge',
        });
    }

    fetchBrands() {
        this.brandService.getBrands().subscribe((res) => {
            this.brands = res.filter(
                (brand) => !brand.name?.includes('Iceium')
            );
            let selectedBrandIds = this.activatedRoute.snapshot.queryParamMap.getAll(
                'brand'
            );

            if (selectedBrandIds) {
                selectedBrandIds.forEach((brandId) => {
                    const temp: Brand[] = this.brands.filter(
                        (b) => b.brandId == +brandId
                    );
                    this.selectedBrands = this.selectedBrands.concat(temp);
                });
            }
        });
    }

    onBrandChange(event: any) {
        // console.log(event.value);
        let extractedBrandIds = event.value.map(
            (brand: Brand) => brand.brandId
        );
        this.router.navigate(['/viewAllProducts'], {
            queryParams: {
                brand: extractedBrandIds,
            },
            queryParamsHandling: 'merge',
        });
    }

    onPriceChange(event: any) {
        console.log(event.values);
        console.log(this.selectedPriceRange);
        this.router.navigate(['/viewAllProducts'], {
            queryParams: {
                priceMin: event.values[0],
                priceMax: event.values[1],
            },
            queryParamsHandling: 'merge',
        });
    }

    setPriceRange() {
        let min = this.activatedRoute.snapshot.queryParamMap.get('priceMin');
        let max = this.activatedRoute.snapshot.queryParamMap.get('priceMax');

        if (min && max) {
            this.selectedPriceRange = [0, 1500];
            this.selectedPriceRange = [+min, +max];
        }
    }

    fetchColors() {
        this.colorService.getColorsWithProducts().subscribe((res) => {
            this.colors = res;
            this.colors.filter((color) => {
                color.specificationEntities?.forEach((spec) => {
                    spec.productEntity?.categoryEntity?.name != 'Customize' &&
                        spec.productEntity?.modelEntity?.brandEntity?.name !=
                            'Iceium';
                });
            });
            let selectedColorIds = this.activatedRoute.snapshot.queryParamMap.getAll(
                'color'
            );

            if (selectedColorIds) {
                selectedColorIds.forEach((colorId) => {
                    const temp: Color[] = this.colors.filter(
                        (c) => c.colorId == +colorId
                    );
                    this.selectedColors = this.selectedColors.concat(temp);
                });
            }
        });
    }

    onColorChange(event: any) {
        // console.log(event.value);
        let extractedColorIds = event.value.map(
            (color: Color) => color.colorId
        );
        this.router.navigate(['/viewAllProducts'], {
            queryParams: {
                color: extractedColorIds,
            },
            queryParamsHandling: 'merge',
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
                    size.sizeString = 'No Size';
                    this.sortedSizes.push(size);
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

            let selectedSizeIds = this.activatedRoute.snapshot.queryParamMap.getAll(
                'size'
            );

            if (selectedSizeIds) {
                selectedSizeIds.forEach((sizeId) => {
                    const temp: Size[] = this.sizes.filter(
                        (s) => s.sizeId == +sizeId
                    );
                    this.selectedSizes = this.selectedSizes.concat(temp);
                });
            }
        });
    }

    onSizeChange(event: any) {
        let extractedSizeIds = event.value.map((size: Size) => size.sizeId);
        this.router.navigate(['/viewAllProducts'], {
            queryParams: {
                size: extractedSizeIds,
            },
            queryParamsHandling: 'merge',
        });
    }
}
