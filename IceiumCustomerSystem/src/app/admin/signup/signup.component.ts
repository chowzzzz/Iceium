import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Customer } from 'src/app/models/customer';
import { CustomerService } from 'src/app/services/customer.service';
import { SessionService } from 'src/app/services/session.service';
import { MessageService } from 'primeng/api';
import { NgForm } from '@angular/forms';

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.css'],
    providers: [MessageService],
})
export class SignupComponent implements OnInit {
    newCustomer: Customer;
    dateOfBirth: number | undefined;
    signupError: boolean;
    registered: boolean;
    errorMessage: string | undefined;
    submitted: boolean;
    yearRange: string = '1921:' + new Date().getFullYear();
    maxDate: Date = new Date();

    constructor(
        public sessionService: SessionService,
        private customerService: CustomerService,
        private messageService: MessageService
    ) {
        this.newCustomer = new Customer();
        this.signupError = false;
        this.submitted = false;
        this.registered = false;
    }

    ngOnInit(): void {}

    clear() {
        this.submitted = false;
        this.newCustomer = new Customer();
    }

    customerRegister(createCustomerForm: NgForm) {
        this.submitted = true;
        this.dateOfBirth = this.newCustomer.dateOfBirth?.getTime();
        let tempCustomer: Customer = Object.assign({}, this.newCustomer);
        tempCustomer.dateOfBirth = undefined;

        if (createCustomerForm.valid) {
            this.customerService
                .customerRegister(tempCustomer, this.dateOfBirth)
                .subscribe(
                    (response) => {
                        let newCustomerId: number = response;

                        this.signupError = false;
                        this.registered = true;

                        // this.customerService
                        //     .customerLogin(
                        //         this.newCustomer.username,
                        //         this.newCustomer.password
                        //     )
                        //     .subscribe(
                        //         (res) => {
                        //             let customer: Customer = res;
                        //             if (customer != null) {
                        //                 this.sessionService.setIsLogin(true);
                        //                 this.sessionService.setCurrentCustomer(
                        //                     customer
                        //                 );
                        //             }
                        //             this.messageService.add({
                        //                 severity: 'success',
                        //                 summary: 'Success',
                        //                 detail:
                        //                     'You have registered successfully and are now logged in!',
                        //             });
                        //         },
                        //         (err) => {
                        //             this.signupError = true;
                        //             this.errorMessage =
                        //                 'An error has occurred while signing in: ' +
                        //                 err;
                        //             console.log(err);
                        //         }
                        //     );
                    },
                    (error) => {
                        this.signupError = true;
                        this.registered = false;
                        this.errorMessage =
                            'An error has occurred while signing in: ' + error;

                        console.log(error);
                    }
                );
        }
    }
}
