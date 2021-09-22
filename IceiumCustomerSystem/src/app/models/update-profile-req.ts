import { Customer } from './customer';

export class UpdateProfileReq {
    currCustomer: Customer | undefined;
    dateOfBirth: number | undefined;

    constructor(currCustomer?: Customer, dateOfBirth?: number) {
        this.currCustomer = currCustomer;
        this.dateOfBirth = dateOfBirth;
    }
}
