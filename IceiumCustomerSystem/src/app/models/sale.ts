import { Product } from './product';
import { Promotion } from './promotion';

export class Sale extends Promotion {
    productEntities: Product[] | undefined;

    constructor(
        promotionId?: number,
        discountRate?: number,
        startDateTime?: Date,
        endDateTime?: Date,
        description?: string,
        currentRedemptions?: number
    ) {
        super(
            promotionId,
            discountRate,
            startDateTime,
            endDateTime,
            description,
            currentRedemptions
        );
    }
}
