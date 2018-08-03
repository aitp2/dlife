import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClockIn } from 'app/shared/model/clock-in.model';

type EntityResponseType = HttpResponse<IClockIn>;
type EntityArrayResponseType = HttpResponse<IClockIn[]>;

@Injectable({ providedIn: 'root' })
export class ClockInService {
    private resourceUrl = SERVER_API_URL + 'api/clock-ins';

    constructor(private http: HttpClient) {}

    create(clockIn: IClockIn): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(clockIn);
        return this.http
            .post<IClockIn>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(clockIn: IClockIn): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(clockIn);
        return this.http
            .put<IClockIn>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IClockIn>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IClockIn[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(clockIn: IClockIn): IClockIn {
        const copy: IClockIn = Object.assign({}, clockIn, {
            punchDateTime: clockIn.punchDateTime != null && clockIn.punchDateTime.isValid() ? clockIn.punchDateTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.punchDateTime = res.body.punchDateTime != null ? moment(res.body.punchDateTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((clockIn: IClockIn) => {
            clockIn.punchDateTime = clockIn.punchDateTime != null ? moment(clockIn.punchDateTime) : null;
        });
        return res;
    }
}
