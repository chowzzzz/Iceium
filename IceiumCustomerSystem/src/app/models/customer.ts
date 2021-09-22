import { CreditCard } from './credit-card';
import { Order } from './order';
import { Address } from './address';

export class Customer {
    customerId: number | undefined;
    firstName: string | undefined;
    lastName: string | undefined;
    dateOfBirth: Date | undefined;
    email: string | undefined;
    username: string | undefined;
    password: string | undefined;
    isEnabled: boolean | undefined;
    creditCardEntities: CreditCard[] | undefined;
    orderEntities: Order[] | undefined;
    addressEntities: Address[] | undefined;
    fullName: string = "";

    constructor(
        customerId?: number,
        firstName?: string,
        lastName?: string,
        dateOfBirth?: Date,
        email?: string,
        username?: string,
        password?: string,
        isEnabled?: boolean
    ) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
    }

    getFullName() {
        return `${this.firstName} ${this.lastName}`;
    }
}
