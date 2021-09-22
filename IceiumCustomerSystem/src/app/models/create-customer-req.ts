import { Customer } from './customer';

export class CreateCustomerReq {
    newCustomer: Customer | undefined;
    dateOfBirth: number | undefined;

    constructor(newCustomer?: Customer, dateOfBirth?: number) {
        this.newCustomer = newCustomer;
        this.dateOfBirth = dateOfBirth;
    }
}
