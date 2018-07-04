import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Attendee } from './attendee.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AttendeeService {

    private resourceUrl =  SERVER_API_URL + 'api/attendees';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(attendee: Attendee): Observable<Attendee> {
        const copy = this.convert(attendee);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(attendee: Attendee): Observable<Attendee> {
        const copy = this.convert(attendee);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Attendee> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Attendee.
     */
    private convertItemFromServer(json: any): Attendee {
        const entity: Attendee = Object.assign(new Attendee(), json);
        entity.participationTime = this.dateUtils
            .convertDateTimeFromServer(json.participationTime);
        return entity;
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
