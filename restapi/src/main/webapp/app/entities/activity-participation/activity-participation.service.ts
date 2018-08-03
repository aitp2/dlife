import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IActivityParticipation } from 'app/shared/model/activity-participation.model';

type EntityResponseType = HttpResponse<IActivityParticipation>;
type EntityArrayResponseType = HttpResponse<IActivityParticipation[]>;

@Injectable({ providedIn: 'root' })
export class ActivityParticipationService {
    private resourceUrl = SERVER_API_URL + 'api/activity-participations';

    constructor(private http: HttpClient) {}

    create(activityParticipation: IActivityParticipation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(activityParticipation);
        return this.http
            .post<IActivityParticipation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(activityParticipation: IActivityParticipation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(activityParticipation);
        return this.http
            .put<IActivityParticipation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IActivityParticipation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IActivityParticipation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(activityParticipation: IActivityParticipation): IActivityParticipation {
        const copy: IActivityParticipation = Object.assign({}, activityParticipation, {
            participationTime:
                activityParticipation.participationTime != null && activityParticipation.participationTime.isValid()
                    ? activityParticipation.participationTime.toJSON()
                    : null,
            latestClockinTime:
                activityParticipation.latestClockinTime != null && activityParticipation.latestClockinTime.isValid()
                    ? activityParticipation.latestClockinTime.toJSON()
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.participationTime = res.body.participationTime != null ? moment(res.body.participationTime) : null;
        res.body.latestClockinTime = res.body.latestClockinTime != null ? moment(res.body.latestClockinTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((activityParticipation: IActivityParticipation) => {
            activityParticipation.participationTime =
                activityParticipation.participationTime != null ? moment(activityParticipation.participationTime) : null;
            activityParticipation.latestClockinTime =
                activityParticipation.latestClockinTime != null ? moment(activityParticipation.latestClockinTime) : null;
        });
        return res;
    }
}
