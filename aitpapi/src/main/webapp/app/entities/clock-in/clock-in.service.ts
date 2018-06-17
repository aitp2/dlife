import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ClockIn } from './clock-in.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ClockIn>;

@Injectable()
export class ClockInService {

    private resourceUrl =  SERVER_API_URL + 'api/clock-ins';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(clockIn: ClockIn): Observable<EntityResponseType> {
        const copy = this.convert(clockIn);
        return this.http.post<ClockIn>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(clockIn: ClockIn): Observable<EntityResponseType> {
        const copy = this.convert(clockIn);
        return this.http.put<ClockIn>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ClockIn>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ClockIn[]>> {
        const options = createRequestOption(req);
        return this.http.get<ClockIn[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ClockIn[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ClockIn = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ClockIn[]>): HttpResponse<ClockIn[]> {
        const jsonResponse: ClockIn[] = res.body;
        const body: ClockIn[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ClockIn.
     */
    private convertItemFromServer(clockIn: ClockIn): ClockIn {
        const copy: ClockIn = Object.assign({}, clockIn);
        copy.punchDateTime = this.dateUtils
            .convertDateTimeFromServer(clockIn.punchDateTime);
        return copy;
    }

    /**
     * Convert a ClockIn to a JSON which can be sent to the server.
     */
    private convert(clockIn: ClockIn): ClockIn {
        const copy: ClockIn = Object.assign({}, clockIn);

        copy.punchDateTime = this.dateUtils.toDate(clockIn.punchDateTime);
        return copy;
    }
}
