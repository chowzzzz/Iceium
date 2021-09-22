import { Injectable } from '@angular/core';
import {
    HttpClient,
    HttpHeaders,
    HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Color } from '../models/color';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
    providedIn: 'root',
})
export class ColorService {
    baseUrl: string = '/api/Color';

    constructor(private httpClient: HttpClient) {}

    getColors(): Observable<Color[]> {
        return this.httpClient
            .get<Color[]>(this.baseUrl + '/retrieveAllColors')
            .pipe(catchError(this.handleError));
    }

    getColorsWithProducts(): Observable<Color[]> {
        return this.httpClient
            .get<Color[]>(this.baseUrl + '/retrieveAllColorsWithProducts')
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
