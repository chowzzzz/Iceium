import { Injectable } from '@angular/core';
import {
    HttpClient,
    HttpHeaders,
    HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Product } from '../models/product';
import { Specification } from '../models/specification';
import { CreateCustomizeProductReq } from '../models/create-customize-product-req';
import { Size } from '../models/size';
import { Color } from '../models/color';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
    providedIn: 'root',
})
export class ProductService {
    baseUrl: string = '/api/Product';

    constructor(private httpClient: HttpClient) {}

    createCustomizeProduct(
        newProductEntity: Product | undefined,
        modelId: number | undefined,
        specificationEntity: Specification | undefined,
        colorEntity: Color | undefined,
        sizeEntity: Size | undefined
    ): Observable<Product> {
        let createCustomizeProductReq: CreateCustomizeProductReq = new CreateCustomizeProductReq(
            newProductEntity,
            modelId,
            specificationEntity,
            colorEntity,
            sizeEntity
        );

        return this.httpClient
            .put<Product>(this.baseUrl, createCustomizeProductReq, httpOptions)
            .pipe(catchError(this.handleError));
    }

    getProducts(): Observable<Product[]> {
        return this.httpClient
            .get<Product[]>(this.baseUrl + '/retrieveProducts')
            .pipe(catchError(this.handleError));
    }

    searchProducts(searchString: string): Observable<Product[]> {
        return this.httpClient
            .get<Product[]>(this.baseUrl + '/retrieveProducts/' + searchString)
            .pipe(catchError(this.handleError));
    }

    getProduct(productId: number): Observable<Product> {
        return this.httpClient
            .get<Product>(this.baseUrl + '/retrieveProduct/' + productId)
            .pipe(catchError(this.handleError));
    }

    getProductBySpecificationId(specificationId: number): Observable<Product> {
        return this.httpClient
            .get<Product>(
                this.baseUrl +
                    '/retrieveProductBySpecificationId/' +
                    specificationId
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
