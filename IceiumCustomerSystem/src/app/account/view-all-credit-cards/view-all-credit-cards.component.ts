import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { SessionService } from 'src/app/services/session.service';
import { ConfirmationService } from 'primeng/api';
import { MessageService } from 'primeng/api';
import { CreditCardService } from 'src/app/services/credit-card.service';

import { Customer } from 'src/app/models/customer';
import { CreditCard } from 'src/app/models/credit-card';
import { CustomerService } from 'src/app/services/customer.service';

@Component({
    selector: 'app-view-all-credit-cards',
    templateUrl: './view-all-credit-cards.component.html',
    styleUrls: ['./view-all-credit-cards.component.css'],
    providers: [MessageService, ConfirmationService],
})
export class ViewAllCreditCardsComponent implements OnInit {
    deleteCreditCardError: boolean;
    errorMessage: string | undefined;

    creditCards: CreditCard[];
    currCustomer: Customer;

    constructor(
        public sessionService: SessionService,
        private router: Router,
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private creditCardService: CreditCardService,
        private customerService: CustomerService
    ) {
        this.deleteCreditCardError = false;
        this.currCustomer = this.sessionService.getCurrentCustomer();
        this.creditCards = new Array();
    }

    ngOnInit(): void {
        this.checkLogin();

        if (this.currCustomer.creditCardEntities) {
            this.creditCards = this.currCustomer.creditCardEntities;
            this.creditCards.forEach((cc) => {
                cc.creditCardNumber = cc.creditCardNumber
                    ?.replace(/.(?=.{4})/g, '*')
                    .replace(/.{4}(?=.)/g, '$& ');
            });
        }
    }

    deleteCreditCard(creditCard: CreditCard) {
        this.confirmationService.confirm({
            message:
                'Are you sure you want to delete Credit Card Number: ' +
                creditCard.creditCardNumber +
                '?',
            header: 'Confirm',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.creditCardService
                    .deleteCreditCard(creditCard.creditCardId)
                    .subscribe(
                        (res) => {
                            this.currCustomer.creditCardEntities?.filter(
                                (cc) =>
                                    cc.creditCardId !== creditCard.creditCardId
                            );
                            this.customerService
                                .getCustomerByCustomerId(
                                    this.sessionService.getCurrentCustomer()
                                        .customerId!
                                )
                                .subscribe((cust) => {
                                    this.sessionService.setCurrentCustomer(
                                        cust
                                    );
                                    this.creditCards = cust.creditCardEntities!;
                                });

                            this.deleteCreditCardError = false;
                            this.messageService.add({
                                severity: 'success',
                                summary: 'Successful',
                                detail: 'Credit Card Deleted',
                                life: 3000,
                            });
                        },
                        (error) => {
                            this.deleteCreditCardError = true;
                            this.errorMessage =
                                'An error has occurred while deleting credit card: ' +
                                error;
                            console.log(error);
                        }
                    );
                // this.creditCards = this.creditCards.filter(
                //     (val) => val.creditCardId !== creditCard.creditCardId
                // );
            },
        });
    }

    checkLogin() {
        if (!this.sessionService.getIsLogin()) {
            this.router.navigate(['/accessRightError']);
        }
    }
}
