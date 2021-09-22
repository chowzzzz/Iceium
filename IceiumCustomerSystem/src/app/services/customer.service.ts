import { Injectable } from '@angular/core';
import {
    HttpClient,
    HttpHeaders,
    HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Customer } from '../models/customer';
import { CustomerChangePasswordReq } from '../models/customer-change-password-req';
import { SessionService } from './session.service';
import { CreateCustomerReq } from '../models/create-customer-req';
import { UpdateProfileReq } from '../models/update-profile-req';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
    providedIn: 'root',
})
export class CustomerService {
    baseUrl: string = '/api/Customer';
    constructor(
        private httpClient: HttpClient,
        private sessionService: SessionService
    ) {}

    customerRegister(
        newCustomer: Customer,
        dateOfBirth?: number
    ): Observable<number> {
        let createCustomerReq: CreateCustomerReq = new CreateCustomerReq(
            newCustomer,
            dateOfBirth
        );
        return this.httpClient
            .put<number>(
                this.baseUrl + '/customerRegister',
                createCustomerReq,
                httpOptions
            )
            .pipe(catchError(this.handleError));
    }

    updateProfile(
        currCustomer: Customer,
        dateOfBirth?: number
    ): Observable<number> {
        let updateProfileReq: UpdateProfileReq = new UpdateProfileReq(
            currCustomer,
            dateOfBirth
        );
        return this.httpClient
            .post<number>(
                this.baseUrl + '/updateProfile',
                updateProfileReq,
                httpOptions
            )
            .pipe(catchError(this.handleError));
    }

    customerLogin(
        username: string | undefined,
        password: string | undefined
    ): Observable<Customer> {
        return this.httpClient
            .get<Customer>(
                this.baseUrl +
                    '/customerLogin?username=' +
                    username +
                    '&password=' +
                    password
            )
            .pipe(catchError(this.handleError));
    }

    getCustomerByCustomerId(customerId: number): Observable<Customer> {
        return this.httpClient
            .get<Customer>(
                this.baseUrl + '/retrieveCustomerByCustomerId/' + customerId
            )
            .pipe(catchError(this.handleError));
    }

    changePassword(oldPwd: string, newPwd: string): Observable<any> {
        let customerChangePasswordReq: CustomerChangePasswordReq = new CustomerChangePasswordReq(
            this.sessionService.getUsername(),
            oldPwd,
            newPwd
        );

        return this.httpClient
            .post<any>(
                this.baseUrl + '/changePassword',
                customerChangePasswordReq,
                httpOptions
            )
            .pipe(catchError(this.handleError));
    }

    private handleError(error: HttpErrorResponse) {
        let errorMessage: string = '';

        if (error.error instanceof ErrorEvent) {
            errorMessage = 'An unknown error has occurred: ' + error.error;
        } else {
            errorMessage =
                'A HTTP error has occurred: ' +
                `HTTP ${error.status}: ${error.statusText}`;
        }

        console.error(`HTTP ${error.status}: ${error.error}`);

        return throwError(errorMessage);
    }
}
