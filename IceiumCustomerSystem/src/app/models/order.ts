import { Address } from './address';
import { Coupon } from './coupon';
import { Customer } from './customer';
import { PaymentMethodEnum } from './enum/payment-method-enum.enum';
import { OrderItem } from './order-item';

export class Order {
    orderId: number | undefined;
    serialNumber: string | undefined;
    totalOrderItem: number | undefined;
    totalQuantity: number | undefined;
    totalAmount: number | undefined;
    transactionDateTime: Date | undefined;
    paymentMethodEnum: PaymentMethodEnum | undefined;
    customerEntity: Customer | undefined;
    addressEntity: Address | undefined;
    orderItemEntities: OrderItem[] | undefined;
    couponEntity: Coupon | undefined;

    constructor(
        totalOrderItem?: number,
        totalQuantity?: number,
        totalAmount?: number,
        transactionDateTime?: Date,
        paymentMethodEnum?: PaymentMethodEnum
    ) {
        this.totalOrderItem = totalOrderItem;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
        this.transactionDateTime = transactionDateTime;
        this.paymentMethodEnum = paymentMethodEnum;

        this.orderItemEntities = new Array<OrderItem>();
    }

    addOrderItem(orderItem: OrderItem | undefined): void {
        if (orderItem != null) {
            this.orderItemEntities?.push(orderItem);
        }
    }
}
