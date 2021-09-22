import { Injectable } from '@angular/core';
import {
    HttpClient,
    HttpHeaders,
    HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CreditCard } from '../models/credit-card';
import { CreateCreditCardReq } from '../models/create-credit-card-req';
import { SessionService } from './session.service';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
    providedIn: 'root',
})
export class CreditCardService {
    baseUrl: string = '/api/CreditCard';

    constructor(
        private httpClient: HttpClient,
        private sessionService: SessionService
    ) {}

    createNewCreditCard(
        newCreditCard: CreditCard,
        expiryDate?: number
    ): Observable<number> {
        let createCreditCardReq: CreateCreditCardReq = new CreateCreditCardReq(
            newCreditCard,
            this.sessionService.getCurrentCustomer(),
            expiryDate
        );

        return this.httpClient
            .put<number>(this.baseUrl, createCreditCardReq, httpOptions)
            .pipe(catchError(this.handleError));
    }

    deleteCreditCard(creditCardId?: number): Observable<any> {
        return this.httpClient
            .delete<any>(
                this.baseUrl +
                    '/' +
                    this.sessionService.getCurrentCustomer().customerId +
                    '/' +
                    creditCardId
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
