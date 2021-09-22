import { Address } from './address';

export class UpdateAddressReq {
    customerId: number | undefined;
    addressEntity: Address | undefined;

    constructor(customerId?: number, addressEntity?: Address) {
        this.customerId = customerId;
        this.addressEntity = addressEntity;
    }
}
