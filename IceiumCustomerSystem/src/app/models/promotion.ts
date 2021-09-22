export class Promotion {
    promotionId: number | undefined;
    discountRate: number | undefined;
    startDateTime: Date | undefined;
    endDateTime: Date | undefined;
    description: string | undefined;
    currentRedemptions: number | undefined;

    constructor(
        promotionId?: number,
        discountRate?: number,
        startDateTime?: Date,
        endDateTime?: Date,
        description?: string,
        currentRedemptions?: number
    ) {
        this.promotionId = promotionId;
        this.discountRate = discountRate;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.currentRedemptions = currentRedemptions;
    }
}
