import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ClockinSummary } from './clockin-summary.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ClockinSummary>;

@Injectable()
export class ClockinSummaryService {

    private resourceUrl =  SERVER_API_URL + 'api/clockin-summaries';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(clockinSummary: ClockinSummary): Observable<EntityResponseType> {
        const copy = this.convert(clockinSummary);
        return this.http.post<ClockinSummary>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(clockinSummary: ClockinSummary): Observable<EntityResponseType> {
        const copy = this.convert(clockinSummary);
        return this.http.put<ClockinSummary>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ClockinSummary>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ClockinSummary[]>> {
        const options = createRequestOption(req);
        return this.http.get<ClockinSummary[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ClockinSummary[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ClockinSummary = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ClockinSummary[]>): HttpResponse<ClockinSummary[]> {
        const jsonResponse: ClockinSummary[] = res.body;
        const body: ClockinSummary[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ClockinSummary.
     */
    private convertItemFromServer(clockinSummary: ClockinSummary): ClockinSummary {
        const copy: ClockinSummary = Object.assign({}, clockinSummary);
        copy.lastClockInTime = this.dateUtils
            .convertDateTimeFromServer(clockinSummary.lastClockInTime);
        return copy;
    }

    /**
     * Convert a ClockinSummary to a JSON which can be sent to the server.
     */
    private convert(clockinSummary: ClockinSummary): ClockinSummary {
        const copy: ClockinSummary = Object.assign({}, clockinSummary);

        copy.lastClockInTime = this.dateUtils.toDate(clockinSummary.lastClockInTime);
        return copy;
    }
}
