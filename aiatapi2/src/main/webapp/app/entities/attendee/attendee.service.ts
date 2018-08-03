import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAttendee } from 'app/shared/model/attendee.model';

type EntityResponseType = HttpResponse<IAttendee>;
type EntityArrayResponseType = HttpResponse<IAttendee[]>;

@Injectable({ providedIn: 'root' })
export class AttendeeService {
    private resourceUrl = SERVER_API_URL + 'api/attendees';

    constructor(private http: HttpClient) {}

    create(attendee: IAttendee): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(attendee);
        return this.http
            .post<IAttendee>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(attendee: IAttendee): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(attendee);
        return this.http
            .put<IAttendee>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAttendee>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAttendee[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(attendee: IAttendee): IAttendee {
        const copy: IAttendee = Object.assign({}, attendee, {
            participationTime:
                attendee.participationTime != null && attendee.participationTime.isValid() ? attendee.participationTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.participationTime = res.body.participationTime != null ? moment(res.body.participationTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((attendee: IAttendee) => {
            attendee.participationTime = attendee.participationTime != null ? moment(attendee.participationTime) : null;
        });
        return res;
    }
}
