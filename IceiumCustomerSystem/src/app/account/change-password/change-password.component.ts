import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Customer } from 'src/app/models/customer';
import { SessionService } from 'src/app/services/session.service';
import { CustomerService } from 'src/app/services/customer.service';
import { MessageService } from 'primeng/api';

@Component({
    selector: 'app-change-password',
    templateUrl: './change-password.component.html',
    styleUrls: ['./change-password.component.css'],
    providers: [MessageService],
})
export class ChangePasswordComponent implements OnInit {
    currCustomer: Customer;
    currPwd: string | undefined;
    oldPwd: string | undefined;
    newPwd: string | undefined;
    confirmPwd: string | undefined;
    submitted: boolean;
    changePasswordError: boolean;
    errorMessage: string | undefined;

    constructor(
        public sessionService: SessionService,
        private router: Router,
        private customerService: CustomerService,
        private messageService: MessageService
    ) 
    {
        this.currCustomer = this.sessionService.getCurrentCustomer();
        this.currPwd = this.sessionService.getPassword();
        this.changePasswordError = false;
        this.submitted = false;
    }

    ngOnInit(): void {
        this.checkLogin();
    }

    checkLogin() {
        if (!this.sessionService.getIsLogin()) {
            this.router.navigate(['/accessRightError']);
        }
    }

    changePassword(changePasswordForm: NgForm)
    {        
        this.submitted = true;

        if (changePasswordForm.invalid)
        {
            this.messageService.add(
                {
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Form is not filled up',
                }
            );
        }
        else if (this.oldPwd === this.currPwd && this.newPwd === this.confirmPwd)
        {
            this.customerService
                .changePassword(this.oldPwd!, this.newPwd!)
                .subscribe(
                    (response) => {
                        this.sessionService.setPassword(this.confirmPwd);
                        this.currPwd = this.sessionService.getPassword();

                        this.changePasswordError = false;
                        this.submitted = false;

                        this.oldPwd = undefined;
                        this.newPwd = undefined;
                        this.confirmPwd = undefined;  

                        changePasswordForm.form.markAsPristine();
                        changePasswordForm.form.markAsUntouched();
                        changePasswordForm.form.updateValueAndValidity();

                        this.messageService.add(
                            {
                                severity: 'success',
                                summary: 'Success',
                                detail:
                                    'You have changed your password sucessfully!',
                            }
                        );
                    },
                    (error) => {
                        this.changePasswordError = true;
                        this.errorMessage =
                            'An error has occurred while changing the password: ' +
                            error;

                            console.log(error);

                        this.messageService.add(
                            {
                                severity: 'error',
                                summary: 'Error',
                                detail:
                                'An error has occurred while changing the password: ' +
                                error,
                            }
                        )
                    }
                )
        }
        else
        {
            this.changePasswordError = true;

            if (this.oldPwd != this.currPwd)
            {
                this.messageService.add(
                    {
                        severity: 'error',
                        summary: 'Error',
                        detail: 'Incorrect current password',
                    }
                );
            }
            else if (this.newPwd != this.confirmPwd)
            {
                this.messageService.add(
                    {
                        severity: 'warn',
                        summary: 'Warn',
                        detail: 'New password and confirm password do not match',
                    }
                );
            }
        }
    }
}
