import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Evaluate } from './evaluate.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Evaluate>;

@Injectable()
export class EvaluateService {

    private resourceUrl =  SERVER_API_URL + 'api/evaluates';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(evaluate: Evaluate): Observable<EntityResponseType> {
        const copy = this.convert(evaluate);
        return this.http.post<Evaluate>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(evaluate: Evaluate): Observable<EntityResponseType> {
        const copy = this.convert(evaluate);
        return this.http.put<Evaluate>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Evaluate>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Evaluate[]>> {
        const options = createRequestOption(req);
        return this.http.get<Evaluate[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Evaluate[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Evaluate = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Evaluate[]>): HttpResponse<Evaluate[]> {
        const jsonResponse: Evaluate[] = res.body;
        const body: Evaluate[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Evaluate.
     */
    private convertItemFromServer(evaluate: Evaluate): Evaluate {
        const copy: Evaluate = Object.assign({}, evaluate);
        copy.createTime = this.dateUtils
            .convertDateTimeFromServer(evaluate.createTime);
        copy.modifyTime = this.dateUtils
            .convertDateTimeFromServer(evaluate.modifyTime);
        return copy;
    }

    /**
     * Convert a Evaluate to a JSON which can be sent to the server.
     */
    private convert(evaluate: Evaluate): Evaluate {
        const copy: Evaluate = Object.assign({}, evaluate);

        copy.createTime = this.dateUtils.toDate(evaluate.createTime);

        copy.modifyTime = this.dateUtils.toDate(evaluate.modifyTime);
        return copy;
    }
}
