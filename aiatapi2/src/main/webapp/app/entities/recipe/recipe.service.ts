import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Recipe } from './recipe.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RecipeService {

    private resourceUrl =  SERVER_API_URL + 'api/recipes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(recipe: Recipe): Observable<Recipe> {
        const copy = this.convert(recipe);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(recipe: Recipe): Observable<Recipe> {
        const copy = this.convert(recipe);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Recipe> {
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
     * Convert a returned JSON object to Recipe.
     */
    private convertItemFromServer(json: any): Recipe {
        const entity: Recipe = Object.assign(new Recipe(), json);
        entity.startTime = this.dateUtils
            .convertDateTimeFromServer(json.startTime);
        entity.endTime = this.dateUtils
            .convertDateTimeFromServer(json.endTime);
        entity.createTime = this.dateUtils
            .convertDateTimeFromServer(json.createTime);
        entity.modifyTime = this.dateUtils
            .convertDateTimeFromServer(json.modifyTime);
        return entity;
    }

    /**
     * Convert a Recipe to a JSON which can be sent to the server.
     */
    private convert(recipe: Recipe): Recipe {
        const copy: Recipe = Object.assign({}, recipe);

        copy.startTime = this.dateUtils.toDate(recipe.startTime);

        copy.endTime = this.dateUtils.toDate(recipe.endTime);

        copy.createTime = this.dateUtils.toDate(recipe.createTime);

        copy.modifyTime = this.dateUtils.toDate(recipe.modifyTime);
        return copy;
    }
}
