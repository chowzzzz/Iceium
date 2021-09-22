import { CreditCard } from './credit-card';
import { Customer } from './customer';

export class CreateCreditCardReq {
    newCreditCardEntity: CreditCard | undefined;
    customerEntity: Customer | undefined;
    expiryDate: number | undefined;

    constructor(newCreditCardEntity?: CreditCard, customerEntity?: Customer, expiryDate?: number) {
        this.newCreditCardEntity = newCreditCardEntity;
        this.customerEntity = customerEntity;
        this.expiryDate = expiryDate;
    }
}
