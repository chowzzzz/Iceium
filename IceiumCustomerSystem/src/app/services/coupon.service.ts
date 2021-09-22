import { Injectable } from '@angular/core';
import {
    HttpClient,
    HttpHeaders,
    HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Coupon } from '../models/coupon';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
    providedIn: 'root',
})
export class CouponService {
    baseUrl: string = '/api/Coupon';

    constructor(private httpClient: HttpClient) {}

    retrieveCouponByCouponCode(couponCode: String): Observable<Coupon> {
        return this.httpClient
            .post<Coupon>(
                this.baseUrl + '/retrieveCouponByCouponCode',
                couponCode,
                httpOptions
            )
            .pipe(catchError(this.handleError));
    }

    checkCouponExistByCouponCode(couponCode: String): Observable<boolean> {
        return this.httpClient
            .post<boolean>(
                this.baseUrl + '/checkCouponExistByCouponCode',
                couponCode,
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
