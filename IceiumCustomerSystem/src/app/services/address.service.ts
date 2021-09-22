import { Injectable } from '@angular/core';
import {
    HttpClient,
    HttpHeaders,
    HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Address } from '../models/address';
import { SessionService } from './session.service';
import { UpdateAddressReq } from '../models/update-address-req';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
    providedIn: 'root',
})
export class AddressService {
    baseUrl: string = '/api/Address';

    constructor(
        private httpClient: HttpClient,
        private sessionService: SessionService
    ) {}

    createAddress(newAddress: Address): Observable<number> {
        return this.httpClient
            .put<number>(
                this.baseUrl +
                    '/createAddress/' +
                    this.sessionService.getCurrentCustomer().customerId,
                newAddress,
                httpOptions
            )
            .pipe(catchError(this.handleError));
    }

    getAddressByAddressId(addressId: number): Observable<Address> {
        return this.httpClient
            .get<Address>(
                this.baseUrl + '/retrieveAddressByAddressId/' + addressId
            )
            .pipe(catchError(this.handleError));
    }

    getAddressByAddressAndPostalCode(
        address: String,
        postalCode: String
    ): Observable<Address> {
        return this.httpClient
            .get<Address>(
                this.baseUrl +
                    '/retrieveAddressByAddressAndPostalCode/' +
                    address +
                    '/' +
                    postalCode
            )
            .pipe(catchError(this.handleError));
    }

    updateAddress(address?: Address): Observable<any> {
        let updateAddressReq: UpdateAddressReq = new UpdateAddressReq(
            this.sessionService.getCurrentCustomer().customerId,
            address
        );
        return this.httpClient.post<any>(
            this.baseUrl,
            updateAddressReq,
            httpOptions
        );
    }

    deleteAddress(addressId?: number): Observable<any> {
        return this.httpClient
            .delete<any>(
                this.baseUrl +
                    '/' +
                    this.sessionService.getCurrentCustomer().customerId +
                    '/' +
                    addressId
            )
            .pipe(catchError(this.handleError));
    }

    private handleError(error: HttpErrorResponse) {
        // let errorMessage: string = '';

        // if (error.error instanceof ErrorEvent) {
        //     errorMessage = 'An unknown error has occurred: ' + error.error;
        // } else {
        //     errorMessage =
        //         'A HTTP error has occurred: ' +
        //         `HTTP ${error.status}: ${error.statusText}`;
        // }

        console.error(`HTTP ${error.status}: ${error.error}`);

        return throwError(error);
    }
}
