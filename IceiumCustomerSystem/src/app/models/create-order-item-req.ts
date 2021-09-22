import { OrderItem } from './order-item';

export class CreateOrderItemReq {
    newOrderItemEntity: OrderItem | undefined;
    productId: number | undefined;

    constructor(newOrderItemEntity?: OrderItem, productId?: number) {
        this.newOrderItemEntity = newOrderItemEntity;
        this.productId = productId;
    }
}
