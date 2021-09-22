import { Injectable } from '@angular/core';
import {
    HttpClient,
    HttpHeaders,
    HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Brand } from '../models/brand';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
    providedIn: 'root',
})
export class BrandService {
    baseUrl: string = '/api/Brand';

    constructor(private httpClient: HttpClient) {}

    getBrands(): Observable<Brand[]> {
        return this.httpClient
            .get<Brand[]>(this.baseUrl + '/retrieveAllBrands')
            .pipe(catchError(this.handleError));
    }

    getBrand(brandId: number): Observable<Brand> {
        return this.httpClient
            .get<Brand>(this.baseUrl + '/retrieveBrand/' + brandId)
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
