import { Order } from './order';
import { Promotion } from './promotion';

export class Coupon extends Promotion {
    couponCode: string | undefined;
    minimumSpend: number | undefined;
    maximumRedemptions: number | undefined;

    constructor(
        promotionId?: number,
        discountRate?: number,
        startDateTime?: Date,
        endDateTime?: Date,
        description?: string,
        currentRedemptions?: number,
        couponCode?: string,
        minimumSpend?: number,
        maximumRedemptions?: number
    ) {
        super(
            promotionId,
            discountRate,
            startDateTime,
            endDateTime,
            description,
            currentRedemptions
        );
        this.couponCode = couponCode;
        this.minimumSpend = minimumSpend;
        this.maximumRedemptions = maximumRedemptions;
    }
}
