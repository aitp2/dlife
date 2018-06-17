import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PinFanActivity } from './pin-fan-activity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PinFanActivity>;

@Injectable()
export class PinFanActivityService {

    private resourceUrl =  SERVER_API_URL + 'api/pin-fan-activities';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(pinFanActivity: PinFanActivity): Observable<EntityResponseType> {
        const copy = this.convert(pinFanActivity);
        return this.http.post<PinFanActivity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(pinFanActivity: PinFanActivity): Observable<EntityResponseType> {
        const copy = this.convert(pinFanActivity);
        return this.http.put<PinFanActivity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PinFanActivity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PinFanActivity[]>> {
        const options = createRequestOption(req);
        return this.http.get<PinFanActivity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PinFanActivity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PinFanActivity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PinFanActivity[]>): HttpResponse<PinFanActivity[]> {
        const jsonResponse: PinFanActivity[] = res.body;
        const body: PinFanActivity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PinFanActivity.
     */
    private convertItemFromServer(pinFanActivity: PinFanActivity): PinFanActivity {
        const copy: PinFanActivity = Object.assign({}, pinFanActivity);
        copy.appointDatetime = this.dateUtils
            .convertDateTimeFromServer(pinFanActivity.appointDatetime);
        copy.deadline = this.dateUtils
            .convertDateTimeFromServer(pinFanActivity.deadline);
        return copy;
    }

    /**
     * Convert a PinFanActivity to a JSON which can be sent to the server.
     */
    private convert(pinFanActivity: PinFanActivity): PinFanActivity {
        const copy: PinFanActivity = Object.assign({}, pinFanActivity);

        copy.appointDatetime = this.dateUtils.toDate(pinFanActivity.appointDatetime);

        copy.deadline = this.dateUtils.toDate(pinFanActivity.deadline);
        return copy;
    }
}
