import { Injectable } from '@angular/core';
import {
    HttpClient,
    HttpHeaders,
    HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { OrderItem } from '../models/order-item';
import { CreateOrderItemReq } from '../models/create-order-item-req';
import { OrderItemStatusEnum } from '../models/enum/order-item-status-enum.enum';
import { UpdateOrderItemReq } from '../models/update-order-item-req';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
    providedIn: 'root',
})
export class OrderItemService {
    baseUrl: string = '/api/OrderItem';

    constructor(private httpClient: HttpClient) {}

    getOrderItemsByOrder(orderId: number): Observable<OrderItem[]> {
        return this.httpClient
            .get<OrderItem[]>(
                this.baseUrl + '/retrieveOrderItemsByOrder/' + orderId
            )
            .pipe(catchError(this.handleError));
    }

    createNewOrderItem(
        newOrderItem: OrderItem,
        productId: number
    ): Observable<number> {
        let createOrderItemReq: CreateOrderItemReq = new CreateOrderItemReq(
            newOrderItem,
            productId
        );

        return this.httpClient
            .put<number>(this.baseUrl, createOrderItemReq, httpOptions)
            .pipe(catchError(this.handleError));
    }

    updateOrderItem(
        orderItemId: number,
        orderItemStatusEnum: OrderItemStatusEnum
    ): Observable<any> {
        let updateOrderItemReq: UpdateOrderItemReq = new UpdateOrderItemReq(
            orderItemId,
            orderItemStatusEnum
        );

        return this.httpClient
            .post<any>(this.baseUrl, updateOrderItemReq, httpOptions)
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
