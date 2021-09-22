import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Customer } from 'src/app/models/customer';
import { CustomerService } from 'src/app/services/customer.service';
import { SessionService } from 'src/app/services/session.service';
import { MessageService } from 'primeng/api';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css'],
    providers: [MessageService],
})
export class ProfileComponent implements OnInit {
    currCustomer: Customer;
    dateOfBirth: number | undefined;
    updateProfileError: boolean;
    errorMessage: string | undefined;
    submitted: boolean;
    yearRange: string = '1921:' + new Date().getFullYear();
    maxDate: Date = new Date();

    constructor(
        public sessionService: SessionService,
        private customerService: CustomerService,
        private router: Router,
        private messageService: MessageService
    ) {
        this.currCustomer = this.sessionService.getCurrentCustomer();
        this.updateProfileError = false;
        this.submitted = false;
    }

    ngOnInit(): void {
        this.checkLogin();
        if (typeof this.currCustomer.dateOfBirth === 'string') {
            this.currCustomer.dateOfBirth = new Date(
                this.currCustomer.dateOfBirth
            );
        }
    }

    updateProfile(updateProfileForm: NgForm) {
        this.submitted = true;
        this.dateOfBirth = this.currCustomer.dateOfBirth?.getTime();
        let tempCustomer: Customer = Object.assign({}, this.currCustomer);
        tempCustomer.dateOfBirth = undefined;
        tempCustomer.password = this.sessionService.getPassword();

        if (updateProfileForm.valid) {
            this.customerService
                .updateProfile(tempCustomer, this.dateOfBirth)
                .subscribe(
                    () => {
                        this.sessionService.setCurrentCustomer(
                            this.currCustomer
                        );
                        this.updateProfileError = false;
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Success',
                            detail:
                                'You have updated your profile sucessfully!',
                        });
                    },
                    (error) => {
                        this.updateProfileError = true;
                        this.errorMessage =
                            'An error has occurred while updating the profile: ' +
                            error;
                        console.log(error);
                    }
                );
        }
    }

    checkLogin() {
        if (!this.sessionService.getIsLogin()) {
            this.router.navigate(['/accessRightError']);
        }
    }

    clear() {
        this.submitted = false;
        this.currCustomer = this.sessionService.getCurrentCustomer();
    }
}
