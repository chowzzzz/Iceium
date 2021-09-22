import { Injectable } from '@angular/core';
import {
    HttpClient,
    HttpHeaders,
    HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Order } from '../models/order';
import { CreateOrderReq } from '../models/create-order-req';
import { SessionService } from './session.service';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
    providedIn: 'root',
})
export class OrderService {
    baseUrl: string = '/api/Order';

    constructor(
        private httpClient: HttpClient,
        private sessionService: SessionService
    ) {}

    createOrder(
        newOrder: Order,
        addressId: number,
        couponId?: number
    ): Observable<number> {
        let customerId = this.sessionService.getCurrentCustomer().customerId;

        let createOrderReq: CreateOrderReq = new CreateOrderReq(
            newOrder,
            customerId,
            addressId,
            couponId
        );

        return this.httpClient
            .put<number>(
                this.baseUrl + '/createOrder',
                createOrderReq,
                httpOptions
            )
            .pipe(catchError(this.handleError));
    }

    getOrdersByCustomer(): Observable<Order[]> {
        return this.httpClient
            .get<Order[]>(
                this.baseUrl +
                    '/retrieveOrdersByCustomer/' +
                    this.sessionService.getCurrentCustomer().customerId
            )
            .pipe(catchError(this.handleError));
    }

    getOrder(orderId: number): Observable<Order> {
        return this.httpClient
            .get<Order>(this.baseUrl + '/retrieveOrder/' + orderId)
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
