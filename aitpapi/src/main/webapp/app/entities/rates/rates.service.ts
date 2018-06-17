import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Rates } from './rates.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Rates>;

@Injectable()
export class RatesService {

    private resourceUrl =  SERVER_API_URL + 'api/rates';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(rates: Rates): Observable<EntityResponseType> {
        const copy = this.convert(rates);
        return this.http.post<Rates>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rates: Rates): Observable<EntityResponseType> {
        const copy = this.convert(rates);
        return this.http.put<Rates>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Rates>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Rates[]>> {
        const options = createRequestOption(req);
        return this.http.get<Rates[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Rates[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Rates = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Rates[]>): HttpResponse<Rates[]> {
        const jsonResponse: Rates[] = res.body;
        const body: Rates[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Rates.
     */
    private convertItemFromServer(rates: Rates): Rates {
        const copy: Rates = Object.assign({}, rates);
        copy.createTime = this.dateUtils
            .convertDateTimeFromServer(rates.createTime);
        copy.modifyTime = this.dateUtils
            .convertDateTimeFromServer(rates.modifyTime);
        return copy;
    }

    /**
     * Convert a Rates to a JSON which can be sent to the server.
     */
    private convert(rates: Rates): Rates {
        const copy: Rates = Object.assign({}, rates);

        copy.createTime = this.dateUtils.toDate(rates.createTime);

        copy.modifyTime = this.dateUtils.toDate(rates.modifyTime);
        return copy;
    }
}
