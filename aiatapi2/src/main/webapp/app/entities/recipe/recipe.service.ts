import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecipe } from 'app/shared/model/recipe.model';

type EntityResponseType = HttpResponse<IRecipe>;
type EntityArrayResponseType = HttpResponse<IRecipe[]>;

@Injectable({ providedIn: 'root' })
export class RecipeService {
    private resourceUrl = SERVER_API_URL + 'api/recipes';

    constructor(private http: HttpClient) {}

    create(recipe: IRecipe): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(recipe);
        return this.http
            .post<IRecipe>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(recipe: IRecipe): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(recipe);
        return this.http
            .put<IRecipe>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRecipe>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRecipe[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(recipe: IRecipe): IRecipe {
        const copy: IRecipe = Object.assign({}, recipe, {
            startTime: recipe.startTime != null && recipe.startTime.isValid() ? recipe.startTime.toJSON() : null,
            endTime: recipe.endTime != null && recipe.endTime.isValid() ? recipe.endTime.toJSON() : null,
            createTime: recipe.createTime != null && recipe.createTime.isValid() ? recipe.createTime.toJSON() : null,
            modifyTime: recipe.modifyTime != null && recipe.modifyTime.isValid() ? recipe.modifyTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.startTime = res.body.startTime != null ? moment(res.body.startTime) : null;
        res.body.endTime = res.body.endTime != null ? moment(res.body.endTime) : null;
        res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
        res.body.modifyTime = res.body.modifyTime != null ? moment(res.body.modifyTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((recipe: IRecipe) => {
            recipe.startTime = recipe.startTime != null ? moment(recipe.startTime) : null;
            recipe.endTime = recipe.endTime != null ? moment(recipe.endTime) : null;
            recipe.createTime = recipe.createTime != null ? moment(recipe.createTime) : null;
            recipe.modifyTime = recipe.modifyTime != null ? moment(recipe.modifyTime) : null;
        });
        return res;
    }
}
