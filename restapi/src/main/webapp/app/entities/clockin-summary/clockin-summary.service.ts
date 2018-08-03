import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClockinSummary } from 'app/shared/model/clockin-summary.model';

type EntityResponseType = HttpResponse<IClockinSummary>;
type EntityArrayResponseType = HttpResponse<IClockinSummary[]>;

@Injectable({ providedIn: 'root' })
export class ClockinSummaryService {
    private resourceUrl = SERVER_API_URL + 'api/clockin-summaries';

    constructor(private http: HttpClient) {}

    create(clockinSummary: IClockinSummary): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(clockinSummary);
        return this.http
            .post<IClockinSummary>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(clockinSummary: IClockinSummary): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(clockinSummary);
        return this.http
            .put<IClockinSummary>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IClockinSummary>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IClockinSummary[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(clockinSummary: IClockinSummary): IClockinSummary {
        const copy: IClockinSummary = Object.assign({}, clockinSummary, {
            lastClockInTime:
                clockinSummary.lastClockInTime != null && clockinSummary.lastClockInTime.isValid()
                    ? clockinSummary.lastClockInTime.toJSON()
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.lastClockInTime = res.body.lastClockInTime != null ? moment(res.body.lastClockInTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((clockinSummary: IClockinSummary) => {
            clockinSummary.lastClockInTime = clockinSummary.lastClockInTime != null ? moment(clockinSummary.lastClockInTime) : null;
        });
        return res;
    }
}
