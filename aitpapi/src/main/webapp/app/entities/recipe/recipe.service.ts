import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Recipe } from './recipe.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Recipe>;

@Injectable()
export class RecipeService {

    private resourceUrl =  SERVER_API_URL + 'api/recipes';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(recipe: Recipe): Observable<EntityResponseType> {
        const copy = this.convert(recipe);
        return this.http.post<Recipe>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(recipe: Recipe): Observable<EntityResponseType> {
        const copy = this.convert(recipe);
        return this.http.put<Recipe>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Recipe>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Recipe[]>> {
        const options = createRequestOption(req);
        return this.http.get<Recipe[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Recipe[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Recipe = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Recipe[]>): HttpResponse<Recipe[]> {
        const jsonResponse: Recipe[] = res.body;
        const body: Recipe[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Recipe.
     */
    private convertItemFromServer(recipe: Recipe): Recipe {
        const copy: Recipe = Object.assign({}, recipe);
        copy.startTime = this.dateUtils
            .convertDateTimeFromServer(recipe.startTime);
        copy.endTime = this.dateUtils
            .convertDateTimeFromServer(recipe.endTime);
        copy.createTime = this.dateUtils
            .convertDateTimeFromServer(recipe.createTime);
        copy.modifyTime = this.dateUtils
            .convertDateTimeFromServer(recipe.modifyTime);
        return copy;
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
