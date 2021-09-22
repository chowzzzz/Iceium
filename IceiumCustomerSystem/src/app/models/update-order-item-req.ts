import { OrderItemStatusEnum } from './enum/order-item-status-enum.enum';

export class UpdateOrderItemReq {
    orderItemId: number | undefined;
    orderItemStatusEnum: OrderItemStatusEnum | undefined;

    constructor(
        orderItemId?: number,
        orderItemStatusEnum?: OrderItemStatusEnum
    ) {
        this.orderItemId = orderItemId;
        this.orderItemStatusEnum = orderItemStatusEnum;
    }
}
