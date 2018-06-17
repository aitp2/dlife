import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ActivityParticipation } from './activity-participation.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ActivityParticipation>;

@Injectable()
export class ActivityParticipationService {

    private resourceUrl =  SERVER_API_URL + 'api/activity-participations';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(activityParticipation: ActivityParticipation): Observable<EntityResponseType> {
        const copy = this.convert(activityParticipation);
        return this.http.post<ActivityParticipation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(activityParticipation: ActivityParticipation): Observable<EntityResponseType> {
        const copy = this.convert(activityParticipation);
        return this.http.put<ActivityParticipation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ActivityParticipation>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ActivityParticipation[]>> {
        const options = createRequestOption(req);
        return this.http.get<ActivityParticipation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ActivityParticipation[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ActivityParticipation = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ActivityParticipation[]>): HttpResponse<ActivityParticipation[]> {
        const jsonResponse: ActivityParticipation[] = res.body;
        const body: ActivityParticipation[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ActivityParticipation.
     */
    private convertItemFromServer(activityParticipation: ActivityParticipation): ActivityParticipation {
        const copy: ActivityParticipation = Object.assign({}, activityParticipation);
        copy.participationTime = this.dateUtils
            .convertDateTimeFromServer(activityParticipation.participationTime);
        return copy;
    }

    /**
     * Convert a ActivityParticipation to a JSON which can be sent to the server.
     */
    private convert(activityParticipation: ActivityParticipation): ActivityParticipation {
        const copy: ActivityParticipation = Object.assign({}, activityParticipation);

        copy.participationTime = this.dateUtils.toDate(activityParticipation.participationTime);
        return copy;
    }
}
