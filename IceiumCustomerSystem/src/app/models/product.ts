import { Category } from './category';
import { Model } from './model';
import { Sale } from './sale';
import { Specification } from './specification';
import { Tag } from './tag';

export class Product {
    productId: number | undefined;
    skuCode: string | undefined;
    name: string | undefined;
    description: string | undefined;
    unitPrice: number | undefined;
    imageLink: string | undefined;
    modelEntity: Model | undefined;
    categoryEntity: Category | undefined;
    tagEntities: Tag[] | undefined;
    specificationEntities: Specification[] | undefined;
    saleEntity: Sale | undefined;

    rating: number | undefined;

    constructor(
        productId?: number,
        skuCode?: string,
        name?: string,
        description?: string,
        unitPrice?: number,
        imageLink?: string
    ) {
        this.productId = productId;
        this.skuCode = skuCode;
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.imageLink = imageLink;
    }
}
