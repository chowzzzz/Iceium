import { Color } from './color';
import { OrderItem } from './order-item';
import { Product } from './product';
import { Size } from './size';

export class Specification {
    specificationId: number | undefined;
    quantityOnHand: number | undefined;
    reorderQuantity: number | undefined;
    productEntity: Product | undefined;
    colorEntity: Color | undefined;
    sizeEntity: Size | undefined;
    orderItemEntities: OrderItem[] | undefined;

    constructor(
        specificationId?: number,
        quantityOnHand?: number,
        reorderQuantity?: number
    ) {
        this.specificationId = specificationId;
        this.quantityOnHand = quantityOnHand;
        this.reorderQuantity = reorderQuantity;
    }
}
