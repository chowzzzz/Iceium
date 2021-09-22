import { Injectable } from '@angular/core';
import {
    HttpClient,
    HttpHeaders,
    HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Model } from '../models/model';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
    providedIn: 'root',
})
export class ModelService {
    baseUrl: string = '/api/Model';

    constructor(private httpClient: HttpClient) {}

    getModels(): Observable<Model[]> {
        return this.httpClient
            .get<Model[]>(this.baseUrl + '/retrieveAllModels')
            .pipe(catchError(this.handleError));
    }

    getModelByName(
        brandName: string | undefined,
        modelName: string | undefined
    ): Observable<Model> {
        return this.httpClient
            .get<Model>(
                this.baseUrl +
                    '/retrieveModelByName?brandName=' +
                    brandName +
                    '&modelName=' +
                    modelName
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
