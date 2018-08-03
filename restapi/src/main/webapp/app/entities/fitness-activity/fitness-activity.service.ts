import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFitnessActivity } from 'app/shared/model/fitness-activity.model';

type EntityResponseType = HttpResponse<IFitnessActivity>;
type EntityArrayResponseType = HttpResponse<IFitnessActivity[]>;

@Injectable({ providedIn: 'root' })
export class FitnessActivityService {
    private resourceUrl = SERVER_API_URL + 'api/fitness-activities';

    constructor(private http: HttpClient) {}

    create(fitnessActivity: IFitnessActivity): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(fitnessActivity);
        return this.http
            .post<IFitnessActivity>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(fitnessActivity: IFitnessActivity): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(fitnessActivity);
        return this.http
            .put<IFitnessActivity>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IFitnessActivity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFitnessActivity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(fitnessActivity: IFitnessActivity): IFitnessActivity {
        const copy: IFitnessActivity = Object.assign({}, fitnessActivity, {
            signStartTime:
                fitnessActivity.signStartTime != null && fitnessActivity.signStartTime.isValid()
                    ? fitnessActivity.signStartTime.toJSON()
                    : null,
            signEndTime:
                fitnessActivity.signEndTime != null && fitnessActivity.signEndTime.isValid() ? fitnessActivity.signEndTime.toJSON() : null,
            activityStartTime:
                fitnessActivity.activityStartTime != null && fitnessActivity.activityStartTime.isValid()
                    ? fitnessActivity.activityStartTime.toJSON()
                    : null,
            activityEndTime:
                fitnessActivity.activityEndTime != null && fitnessActivity.activityEndTime.isValid()
                    ? fitnessActivity.activityEndTime.toJSON()
                    : null,
            modifyTime:
                fitnessActivity.modifyTime != null && fitnessActivity.modifyTime.isValid() ? fitnessActivity.modifyTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.signStartTime = res.body.signStartTime != null ? moment(res.body.signStartTime) : null;
        res.body.signEndTime = res.body.signEndTime != null ? moment(res.body.signEndTime) : null;
        res.body.activityStartTime = res.body.activityStartTime != null ? moment(res.body.activityStartTime) : null;
        res.body.activityEndTime = res.body.activityEndTime != null ? moment(res.body.activityEndTime) : null;
        res.body.modifyTime = res.body.modifyTime != null ? moment(res.body.modifyTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((fitnessActivity: IFitnessActivity) => {
            fitnessActivity.signStartTime = fitnessActivity.signStartTime != null ? moment(fitnessActivity.signStartTime) : null;
            fitnessActivity.signEndTime = fitnessActivity.signEndTime != null ? moment(fitnessActivity.signEndTime) : null;
            fitnessActivity.activityStartTime =
                fitnessActivity.activityStartTime != null ? moment(fitnessActivity.activityStartTime) : null;
            fitnessActivity.activityEndTime = fitnessActivity.activityEndTime != null ? moment(fitnessActivity.activityEndTime) : null;
            fitnessActivity.modifyTime = fitnessActivity.modifyTime != null ? moment(fitnessActivity.modifyTime) : null;
        });
        return res;
    }
}
