import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { RecipeOrder } from './recipe-order.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<RecipeOrder>;

@Injectable()
export class RecipeOrderService {

    private resourceUrl =  SERVER_API_URL + 'api/recipe-orders';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(recipeOrder: RecipeOrder): Observable<EntityResponseType> {
        const copy = this.convert(recipeOrder);
        return this.http.post<RecipeOrder>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(recipeOrder: RecipeOrder): Observable<EntityResponseType> {
        const copy = this.convert(recipeOrder);
        return this.http.put<RecipeOrder>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<RecipeOrder>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RecipeOrder[]>> {
        const options = createRequestOption(req);
        return this.http.get<RecipeOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RecipeOrder[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RecipeOrder = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<RecipeOrder[]>): HttpResponse<RecipeOrder[]> {
        const jsonResponse: RecipeOrder[] = res.body;
        const body: RecipeOrder[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to RecipeOrder.
     */
    private convertItemFromServer(recipeOrder: RecipeOrder): RecipeOrder {
        const copy: RecipeOrder = Object.assign({}, recipeOrder);
        copy.createTime = this.dateUtils
            .convertDateTimeFromServer(recipeOrder.createTime);
        copy.modifyTime = this.dateUtils
            .convertDateTimeFromServer(recipeOrder.modifyTime);
        return copy;
    }

    /**
     * Convert a RecipeOrder to a JSON which can be sent to the server.
     */
    private convert(recipeOrder: RecipeOrder): RecipeOrder {
        const copy: RecipeOrder = Object.assign({}, recipeOrder);

        copy.createTime = this.dateUtils.toDate(recipeOrder.createTime);

        copy.modifyTime = this.dateUtils.toDate(recipeOrder.modifyTime);
        return copy;
    }
}
