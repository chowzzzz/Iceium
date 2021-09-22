import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService, SelectItem } from 'primeng/api';
import { CreditCard } from 'src/app/models/credit-card';
import { PaymentMethodEnum } from 'src/app/models/enum/payment-method-enum.enum';
import { SessionService } from 'src/app/services/session.service';

@Component({
    selector: 'app-payment-method',
    templateUrl: './payment-method.component.html',
    styleUrls: ['./payment-method.component.css'],
    providers: [MessageService],
})
export class PaymentMethodComponent implements OnInit {
    selectedPaymentMethod: SelectItem;
    paymentMethods: SelectItem[];

    creditCard: CreditCard;
    submitted: boolean;
    yearRange: string =
        new Date().getFullYear() + ':' + (new Date().getFullYear() + 10);
    minDate: Date = new Date();

    constructor(
        private router: Router,
        private sessionService: SessionService,
        private messageService: MessageService
    ) {
        let tmp: Object[] = Object.values(PaymentMethodEnum).reverse();
        this.paymentMethods = [
            { label: 'Credit Card', value: tmp[0] },
            { label: 'Cash On Delivery', value: tmp[1] },
        ];
        this.selectedPaymentMethod = this.paymentMethods[0];

        this.creditCard = new CreditCard();
        this.submitted = false;
    }

    ngOnInit(): void {
        let tmpCreditCard = this.sessionService.getCreditCard();

        if (
            this.sessionService.getCurrentCustomer() != null &&
            this.sessionService.getCurrentCustomer().creditCardEntities !=
                null &&
            this.sessionService.getCurrentCustomer().creditCardEntities?.length
        ) {
            this.sessionService
                .getCurrentCustomer()
                .creditCardEntities?.forEach(
                    (creditCard: CreditCard | undefined) => {
                        if (creditCard != null) {
                            this.creditCard = creditCard;
                            if (
                                typeof this.creditCard.expiryDate === 'string'
                            ) {
                                this.creditCard.expiryDate = new Date(
                                    this.creditCard.expiryDate
                                );
                            }
                            return;
                        }
                    }
                );
        } else if (tmpCreditCard) {
            this.creditCard = tmpCreditCard;
            if (typeof this.creditCard.expiryDate === 'string') {
                this.creditCard.expiryDate = new Date(
                    this.creditCard.expiryDate
                );
            }
        }

        let tmpPaymentMethod = this.sessionService.getPaymentMethod();
        if (tmpPaymentMethod) {
            this.selectedPaymentMethod =
                tmpPaymentMethod === 'CREDIT_CARD'
                    ? this.paymentMethods[0]
                    : this.paymentMethods[1];
        }
    }

    prevPage(): void {
        this.router.navigate(['checkout/deliveryAddress']);
    }

    nextPage(paymentMethodForm: NgForm): void {
        this.submitted = true;
        if (paymentMethodForm.valid) {
            this.sessionService.setPaymentMethod(
                this.selectedPaymentMethod.value
            );
            this.sessionService.setCreditCard(this.creditCard);
            this.router.navigate(['checkout/review']);
        } else {
            this.messageService.add({
                severity: 'warn',
                summary: 'Warn',
                detail: 'Creditcard information is not filled in',
            });
        }
    }
}
