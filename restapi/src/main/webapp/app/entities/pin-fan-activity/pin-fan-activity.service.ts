import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPinFanActivity } from 'app/shared/model/pin-fan-activity.model';

type EntityResponseType = HttpResponse<IPinFanActivity>;
type EntityArrayResponseType = HttpResponse<IPinFanActivity[]>;

@Injectable({ providedIn: 'root' })
export class PinFanActivityService {
    private resourceUrl = SERVER_API_URL + 'api/pin-fan-activities';

    constructor(private http: HttpClient) {}

    create(pinFanActivity: IPinFanActivity): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pinFanActivity);
        return this.http
            .post<IPinFanActivity>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(pinFanActivity: IPinFanActivity): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pinFanActivity);
        return this.http
            .put<IPinFanActivity>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPinFanActivity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPinFanActivity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(pinFanActivity: IPinFanActivity): IPinFanActivity {
        const copy: IPinFanActivity = Object.assign({}, pinFanActivity, {
            appointDatetime:
                pinFanActivity.appointDatetime != null && pinFanActivity.appointDatetime.isValid()
                    ? pinFanActivity.appointDatetime.toJSON()
                    : null,
            appointEndDatetime:
                pinFanActivity.appointEndDatetime != null && pinFanActivity.appointEndDatetime.isValid()
                    ? pinFanActivity.appointEndDatetime.toJSON()
                    : null,
            deadline: pinFanActivity.deadline != null && pinFanActivity.deadline.isValid() ? pinFanActivity.deadline.toJSON() : null,
            modifyTime: pinFanActivity.modifyTime != null && pinFanActivity.modifyTime.isValid() ? pinFanActivity.modifyTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.appointDatetime = res.body.appointDatetime != null ? moment(res.body.appointDatetime) : null;
        res.body.appointEndDatetime = res.body.appointEndDatetime != null ? moment(res.body.appointEndDatetime) : null;
        res.body.deadline = res.body.deadline != null ? moment(res.body.deadline) : null;
        res.body.modifyTime = res.body.modifyTime != null ? moment(res.body.modifyTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((pinFanActivity: IPinFanActivity) => {
            pinFanActivity.appointDatetime = pinFanActivity.appointDatetime != null ? moment(pinFanActivity.appointDatetime) : null;
            pinFanActivity.appointEndDatetime =
                pinFanActivity.appointEndDatetime != null ? moment(pinFanActivity.appointEndDatetime) : null;
            pinFanActivity.deadline = pinFanActivity.deadline != null ? moment(pinFanActivity.deadline) : null;
            pinFanActivity.modifyTime = pinFanActivity.modifyTime != null ? moment(pinFanActivity.modifyTime) : null;
        });
        return res;
    }
}
