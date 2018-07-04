import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { RecipeOrder } from './recipe-order.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RecipeOrderService {

    private resourceUrl =  SERVER_API_URL + 'api/recipe-orders';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(recipeOrder: RecipeOrder): Observable<RecipeOrder> {
        const copy = this.convert(recipeOrder);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(recipeOrder: RecipeOrder): Observable<RecipeOrder> {
        const copy = this.convert(recipeOrder);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<RecipeOrder> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to RecipeOrder.
     */
    private convertItemFromServer(json: any): RecipeOrder {
        const entity: RecipeOrder = Object.assign(new RecipeOrder(), json);
        entity.createTime = this.dateUtils
            .convertDateTimeFromServer(json.createTime);
        entity.modifyTime = this.dateUtils
            .convertDateTimeFromServer(json.modifyTime);
        return entity;
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
