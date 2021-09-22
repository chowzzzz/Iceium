import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Address } from 'src/app/models/address';
import { Customer } from 'src/app/models/customer';
import { AddressService } from 'src/app/services/address.service';
import { SessionService } from 'src/app/services/session.service';
import { ConfirmationService } from 'primeng/api';
import { MessageService } from 'primeng/api';
import { CustomerService } from 'src/app/services/customer.service';

@Component({
    selector: 'app-view-all-addresses',
    templateUrl: './view-all-addresses.component.html',
    styleUrls: ['./view-all-addresses.component.css'],
    providers: [MessageService, ConfirmationService],
})
export class ViewAllAddressesComponent implements OnInit {
    deleteAddressError: boolean;
    errorMessage: string | undefined;
    addressDialog: boolean;
    submitted: boolean;

    addresses: Address[];
    address: Address;
    currCustomer: Customer;

    constructor(
        private sessionService: SessionService,
        private router: Router,
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private addressService: AddressService,
        private customerService: CustomerService
    ) {
        this.deleteAddressError = false;
        this.addresses = new Array();
        this.currCustomer = this.sessionService.getCurrentCustomer();
        this.addressDialog = false;
        this.submitted = false;
        this.address = new Address();
    }

    ngOnInit(): void {
        this.checkLogin();
        if (this.currCustomer.addressEntities) {
            this.addresses = this.currCustomer.addressEntities;
        }
    }

    deleteAddress(address: Address) {
        this.confirmationService.confirm({
            message: 'Are you sure you want to delete this address?',
            header: 'Confirm',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.addressService.deleteAddress(address.addressId).subscribe(
                    (res) => {
                        this.currCustomer.addressEntities?.filter(
                            (add) => add.addressId !== address.addressId
                        );
                        this.customerService
                            .getCustomerByCustomerId(
                                this.sessionService.getCurrentCustomer()
                                    .customerId!
                            )
                            .subscribe((cust) => {
                                this.sessionService.setCurrentCustomer(cust);
                                this.addresses = cust.addressEntities!;
                            });

                        this.deleteAddressError = false;
                        this.address = new Address();
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Successful',
                            detail: 'Address Deleted',
                            life: 3000,
                        });
                    },
                    (error) => {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Error',
                            detail:
                                'An error has occurred while adding a deleting address: ' +
                                error,
                        });
                    }
                );
            },
        });
    }

    updateAddress(address: Address) {
        this.address = address;
        this.addressDialog = true;
    }

    hideDialog() {
        this.addressDialog = false;
        this.submitted = false;
    }

    saveAddress() {
        this.submitted = true;

        if (this.address?.address?.trim() && this.address?.postalCode?.trim()) {
            this.addressService.updateAddress(this.address).subscribe(
                (res) => {
                    this.currCustomer.addressEntities?.filter(
                        (add) => add.addressId !== this.address?.addressId
                    );
                    this.customerService
                        .getCustomerByCustomerId(
                            this.sessionService.getCurrentCustomer().customerId!
                        )
                        .subscribe((cust) => {
                            this.sessionService.setCurrentCustomer(cust);
                            this.addresses = cust.addressEntities!;
                        });

                    this.addressDialog = false;
                    this.address = new Address();
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Successful',
                        detail: 'Address Updated',
                        life: 3000,
                    });
                },
                (error) => {
                    if (error.status === 304) {
                        this.hideDialog();
                    } else {
                        let errorMessage: string = '';
                        if (error.error instanceof ErrorEvent) {
                            errorMessage =
                                'An unknown error has occurred: ' + error.error;
                        } else {
                            errorMessage =
                                'A HTTP error has occurred: ' +
                                `HTTP ${error.status}: ${error.error}`;
                        }

                        this.messageService.add({
                            severity: 'error',
                            summary: 'Error',
                            detail:
                                'An error has occurred while updating address: ' +
                                errorMessage,
                            life: 3000,
                        });
                    }
                }
            );
        }
    }

    checkLogin() {
        if (!this.sessionService.getIsLogin()) {
            this.router.navigate(['/accessRightError']);
        }
    }
}
