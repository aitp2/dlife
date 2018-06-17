import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Pics } from './pics.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Pics>;

@Injectable()
export class PicsService {

    private resourceUrl =  SERVER_API_URL + 'api/pics';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(pics: Pics): Observable<EntityResponseType> {
        const copy = this.convert(pics);
        return this.http.post<Pics>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(pics: Pics): Observable<EntityResponseType> {
        const copy = this.convert(pics);
        return this.http.put<Pics>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Pics>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Pics[]>> {
        const options = createRequestOption(req);
        return this.http.get<Pics[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Pics[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Pics = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Pics[]>): HttpResponse<Pics[]> {
        const jsonResponse: Pics[] = res.body;
        const body: Pics[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Pics.
     */
    private convertItemFromServer(pics: Pics): Pics {
        const copy: Pics = Object.assign({}, pics);
        copy.createTime = this.dateUtils
            .convertDateTimeFromServer(pics.createTime);
        return copy;
    }

    /**
     * Convert a Pics to a JSON which can be sent to the server.
     */
    private convert(pics: Pics): Pics {
        const copy: Pics = Object.assign({}, pics);

        copy.createTime = this.dateUtils.toDate(pics.createTime);
        return copy;
    }
}
