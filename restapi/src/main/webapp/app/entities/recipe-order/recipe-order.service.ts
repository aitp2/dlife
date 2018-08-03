import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecipeOrder } from 'app/shared/model/recipe-order.model';

type EntityResponseType = HttpResponse<IRecipeOrder>;
type EntityArrayResponseType = HttpResponse<IRecipeOrder[]>;

@Injectable({ providedIn: 'root' })
export class RecipeOrderService {
    private resourceUrl = SERVER_API_URL + 'api/recipe-orders';

    constructor(private http: HttpClient) {}

    create(recipeOrder: IRecipeOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(recipeOrder);
        return this.http
            .post<IRecipeOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(recipeOrder: IRecipeOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(recipeOrder);
        return this.http
            .put<IRecipeOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRecipeOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRecipeOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(recipeOrder: IRecipeOrder): IRecipeOrder {
        const copy: IRecipeOrder = Object.assign({}, recipeOrder, {
            createTime: recipeOrder.createTime != null && recipeOrder.createTime.isValid() ? recipeOrder.createTime.toJSON() : null,
            modifyTime: recipeOrder.modifyTime != null && recipeOrder.modifyTime.isValid() ? recipeOrder.modifyTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
        res.body.modifyTime = res.body.modifyTime != null ? moment(res.body.modifyTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((recipeOrder: IRecipeOrder) => {
            recipeOrder.createTime = recipeOrder.createTime != null ? moment(recipeOrder.createTime) : null;
            recipeOrder.modifyTime = recipeOrder.modifyTime != null ? moment(recipeOrder.modifyTime) : null;
        });
        return res;
    }
}
