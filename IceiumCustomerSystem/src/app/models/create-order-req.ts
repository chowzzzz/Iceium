import { Order } from './order';

export class CreateOrderReq {
    newOrderEntity: Order | undefined;
    customerId: number | undefined;
    addressId: number | undefined;
    couponId: number | undefined;    

    constructor(newOrderEntity?: Order, customerId?: number, addressId?: number, couponId?:number) {
        this.newOrderEntity = newOrderEntity;
        this.customerId = customerId;
        this.addressId = addressId;        
        this.couponId = couponId;
    }
}
