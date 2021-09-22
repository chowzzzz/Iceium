import { Injectable } from '@angular/core';
import {
    HttpClient,
    HttpHeaders,
    HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Specification } from '../models/specification';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
    providedIn: 'root',
})
export class SpecificationService {
    baseUrl: string = '/api/Specification';

    constructor(private httpClient: HttpClient) {}

    getSpecification(specificationId: number): Observable<Specification> {
        return this.httpClient
            .get<Specification>(
                this.baseUrl + '/retrieveSpecification/' + specificationId
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
