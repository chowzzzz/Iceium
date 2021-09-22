import { Model } from "./model";

export class Address {
    addressId: number | undefined;
    address: string | undefined;
    postalCode: string | undefined;

    constructor(addressId?: number, address?: string, postalCode?: string) {
        this.addressId = addressId;
        this.address = address;
        this.postalCode = postalCode;
    }
}
