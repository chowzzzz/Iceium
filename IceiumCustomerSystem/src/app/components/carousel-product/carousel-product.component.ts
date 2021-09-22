import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';

@Component({
    selector: 'app-carousel-product',
    templateUrl: './carousel-product.component.html',
    styleUrls: ['./carousel-product.component.css'],
})
export class CarouselProductComponent implements OnInit {
    @Input()
    products!: Product[];
    @Input() productType: string | undefined;
    responsiveOptions: Object[];

    constructor() {
        this.responsiveOptions = [
            {
                breakpoint: '1024px',
                numVisible: 3,
                numScroll: 3,
            },
            {
                breakpoint: '768px',
                numVisible: 2,
                numScroll: 2,
            },
            {
                breakpoint: '560px',
                numVisible: 1,
                numScroll: 1,
            },
        ];
    }

    ngOnInit(): void {}
}
