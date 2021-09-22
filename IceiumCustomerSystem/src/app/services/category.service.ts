import { Injectable } from '@angular/core';
import {
    HttpClient,
    HttpHeaders,
    HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Category } from '../models/category';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
    providedIn: 'root',
})
export class CategoryService {
    baseUrl: string = '/api/Category';

    constructor(private httpClient: HttpClient) {}

    getCategories(): Observable<Category[]> {
        return this.httpClient
            .get<Category[]>(this.baseUrl + '/retrieveAllCategories')
            .pipe(catchError(this.handleError));
    }

    getCategory(categoryId: number): Observable<Category> {
        return this.httpClient
            .get<Category>(this.baseUrl + '/retrieveCategory/' + categoryId)
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
