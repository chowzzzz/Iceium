import { Customer } from './customer';

export class CreditCard {
    creditCardId: number | undefined;
    creditCardNumber: string | undefined;
    expiryDate: Date | undefined;
    securityCode: string | undefined;
    nameOnCard: string | undefined;
    customerEntity: Customer | undefined;

    constructor(
        creditCardId?: number,
        creditCardNumber?: string,
        expiryDate?: Date,
        securityCode?: string,
        nameOnCard?: string
    ) {
        this.creditCardId = creditCardId;
        this.creditCardNumber = creditCardNumber;
        this.expiryDate = expiryDate;
        this.securityCode = securityCode;
        this.nameOnCard = nameOnCard;
    }
}
