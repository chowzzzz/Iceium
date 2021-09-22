import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { MessageService } from 'primeng/api';
import { Customer } from 'src/app/models/customer';
import { CreditCard } from 'src/app/models/credit-card';
import { CreditCardService } from 'src/app/services/credit-card.service';
import { CustomerService } from 'src/app/services/customer.service';

@Component({
    selector: 'app-add-new-credit-card',
    templateUrl: './add-new-credit-card.component.html',
    styleUrls: ['./add-new-credit-card.component.css'],
    providers: [MessageService],
})
export class AddNewCreditCardComponent implements OnInit {
    currCustomer: Customer;
    newCreditCard: CreditCard;
    expiryDate: number | undefined;
    expiryDateBackup: Date | undefined;
    createCreditCardError: boolean;
    errorMessage: string | undefined;
    submitted: boolean;
    yearRange: string =
        new Date().getFullYear() + ':' + (new Date().getFullYear() + 10);
    minDate: Date = new Date();

    constructor(
        public sessionService: SessionService,
        private router: Router,
        private messageService: MessageService,
        private creditCardService: CreditCardService,
        private customerService: CustomerService
    ) {
        this.currCustomer = this.sessionService.getCurrentCustomer();
        this.newCreditCard = new CreditCard();
        this.createCreditCardError = false;
        this.submitted = false;
    }

    ngOnInit(): void {
        this.checkLogin();
    }

    clear() {
        this.submitted = false;
        this.newCreditCard = new CreditCard();
    }

    checkLogin() {
        if (!this.sessionService.getIsLogin()) {
            this.router.navigate(['/accessRightError']);
        }
    }

    createNewCreditCard(createNewCreditCardForm: NgForm) {
        this.submitted = true;
        this.expiryDate = this.newCreditCard.expiryDate?.getTime();
        let tempCC: CreditCard = Object.assign({}, this.newCreditCard);
        tempCC.expiryDate = undefined;

        if (createNewCreditCardForm.valid) {
            this.creditCardService
                .createNewCreditCard(tempCC, this.expiryDate)
                .subscribe(
                    (response) => {
                        let newCreditCardId: number = response;

                        this.createCreditCardError = false;

                        this.customerService
                            .getCustomerByCustomerId(
                                this.sessionService.getCurrentCustomer()
                                    .customerId!
                            )
                            .subscribe((cust) => {
                                this.sessionService.setCurrentCustomer(cust);
                            });

                        this.messageService.add({
                            severity: 'success',
                            summary: 'Success',
                            detail:
                                'You have added a new credit card sucessfully!',
                        });
                    },
                    (error) => {
                        this.createCreditCardError = true;
                        this.errorMessage =
                            'An error has occurred while adding the new credit card: ' +
                            error;

                        console.log(error);

                        this.messageService.add({
                            severity: 'error',
                            summary: 'Error',
                            detail:
                                'An error has occurred while adding the new credit card: ' +
                                error,
                        });
                    }
                );
        }
    }
}
