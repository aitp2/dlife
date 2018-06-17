import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Attendee } from './attendee.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Attendee>;

@Injectable()
export class AttendeeService {

    private resourceUrl =  SERVER_API_URL + 'api/attendees';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(attendee: Attendee): Observable<EntityResponseType> {
        const copy = this.convert(attendee);
        return this.http.post<Attendee>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(attendee: Attendee): Observable<EntityResponseType> {
        const copy = this.convert(attendee);
        return this.http.put<Attendee>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Attendee>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Attendee[]>> {
        const options = createRequestOption(req);
        return this.http.get<Attendee[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Attendee[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Attendee = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Attendee[]>): HttpResponse<Attendee[]> {
        const jsonResponse: Attendee[] = res.body;
        const body: Attendee[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Attendee.
     */
    private convertItemFromServer(attendee: Attendee): Attendee {
        const copy: Attendee = Object.assign({}, attendee);
        copy.participationTime = this.dateUtils
            .convertDateTimeFromServer(attendee.participationTime);
        return copy;
    }

    /**
     * Convert a Attendee to a JSON which can be sent to the server.
     */
    private convert(attendee: Attendee): Attendee {
        const copy: Attendee = Object.assign({}, attendee);

        copy.participationTime = this.dateUtils.toDate(attendee.participationTime);
        return copy;
    }
}
