import { Address } from 'src/app/models/address';
import { AddressService } from 'src/app/services/address.service';
import { Component, OnInit } from '@angular/core';
import { Customer } from 'src/app/models/customer';
import { CustomerService } from 'src/app/services/customer.service';
import { MessageService } from 'primeng/api';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';

@Component({
    selector: 'app-add-new-address',
    templateUrl: './add-new-address.component.html',
    styleUrls: ['./add-new-address.component.css'],
    providers: [MessageService],
})
export class AddNewAddressComponent implements OnInit {
    currCustomer: Customer;
    newAddress: Address;
    addAddressError: boolean;
    errorMessage: string | undefined;
    submitted: boolean;

    constructor(
        public sessionService: SessionService,
        private router: Router,
        private messageService: MessageService,
        private addressService: AddressService,
        private customerService: CustomerService
    ) {
        this.currCustomer = this.sessionService.getCurrentCustomer();
        this.newAddress = new Address();
        this.addAddressError = false;
        this.submitted = false;
    }

    ngOnInit(): void {
        this.checkLogin();
    }

    addNewAddress(addNewAddressForm: NgForm) {
        this.submitted = true;

        if (addNewAddressForm.valid) {
            if (this.newAddress) {
                this.addressService.createAddress(this.newAddress).subscribe(
                    (response) => {
                        if (this.currCustomer.customerId) {
                            this.customerService
                                .getCustomerByCustomerId(
                                    this.currCustomer.customerId
                                )
                                .subscribe(
                                    (response) => {
                                        this.currCustomer = response;
                                        this.sessionService.setCurrentCustomer(
                                            this.currCustomer
                                        );
                                    },
                                    (error) => {
                                        this.messageService.add({
                                            severity: 'error',
                                            summary: 'Error',
                                            detail:
                                                'An error has occurred while adding a new address: ' +
                                                error,
                                        });
                                    }
                                );
                        }

                        this.submitted = false;

                        this.newAddress = new Address();

                        addNewAddressForm.form.markAsPristine();
                        addNewAddressForm.form.markAsUntouched();
                        addNewAddressForm.form.updateValueAndValidity();

                        this.messageService.add({
                            severity: 'success',
                            summary: 'Success',
                            detail: 'You have added a new address sucessfully!',
                        });
                    },
                    (error) => {
                        let errorMessage: string = '';

                        if (error.error instanceof ErrorEvent) {
                            errorMessage =
                                'An unknown error has occurred: ' + error.error;
                        } else {
                            errorMessage =
                                'A HTTP error has occurred: ' +
                                `HTTP ${error.status}: ${error.statusText}`;
                        }
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Error',
                            detail:
                                'An error has occurred while adding a new address: ' +
                                errorMessage,
                        });
                    }
                );
            }
        } else {
            this.addAddressError = true;
            this.messageService.add({
                severity: 'warn',
                summary: 'Warn',
                detail: 'The form is not filled up',
            });
        }
    }
    checkLogin() {
        if (!this.sessionService.getIsLogin()) {
            this.router.navigate(['/accessRightError']);
        }
    }
}
