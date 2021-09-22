import { OrderItemStatusEnum } from './enum/order-item-status-enum.enum';
import { Order } from './order';
import { Product } from './product';
import { Review } from './review';
import { Specification } from './specification';

export class OrderItem {
    orderItemId: number | undefined;
    orderItemNumber: number | undefined;
    quantity: number | undefined;
    subTotal: number | undefined;
    orderItemStatusEnum: OrderItemStatusEnum | undefined;
    specificationEntity: Specification | undefined;
    orderEntity: Order | undefined;
    reviewEntity: Review | undefined;

    specificationId: number | undefined;
    productId: number | undefined;
    productEntity: Product | undefined;

    constructor(
        orderItemNumber?: number,
        quantity?: number,
        subTotal?: number
    ) {
        this.orderItemNumber = orderItemNumber;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }
}
