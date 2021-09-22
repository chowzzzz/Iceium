import { OrderItem } from "./order-item";

export class Review {
    reviewId: number | undefined;
    rating: number | undefined;
    review: string | undefined;
    dateOfReview: Date | undefined;
    orderItemEntity: OrderItem | undefined;

    constructor(
        reviewId?: number,
        rating?: number,
        review?: string,
        dateOfReview?: Date
    ) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.review = review;
        this.dateOfReview = dateOfReview;
    }
}
