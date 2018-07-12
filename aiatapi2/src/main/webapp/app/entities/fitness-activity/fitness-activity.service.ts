import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { FitnessActivity } from './fitness-activity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<FitnessActivity>;

@Injectable()
export class FitnessActivityService {

    private resourceUrl =  SERVER_API_URL + 'api/fitness-activities';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(fitnessActivity: FitnessActivity): Observable<EntityResponseType> {
        const copy = this.convert(fitnessActivity);
        return this.http.post<FitnessActivity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(fitnessActivity: FitnessActivity): Observable<EntityResponseType> {
        const copy = this.convert(fitnessActivity);
        return this.http.put<FitnessActivity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<FitnessActivity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<FitnessActivity[]>> {
        const options = createRequestOption(req);
        return this.http.get<FitnessActivity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<FitnessActivity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: FitnessActivity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<FitnessActivity[]>): HttpResponse<FitnessActivity[]> {
        const jsonResponse: FitnessActivity[] = res.body;
        const body: FitnessActivity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to FitnessActivity.
     */
    private convertItemFromServer(fitnessActivity: FitnessActivity): FitnessActivity {
        const copy: FitnessActivity = Object.assign({}, fitnessActivity);
        copy.signStartTime = this.dateUtils
            .convertDateTimeFromServer(fitnessActivity.signStartTime);
        copy.signEndTime = this.dateUtils
            .convertDateTimeFromServer(fitnessActivity.signEndTime);
        copy.activityStartTime = this.dateUtils
            .convertDateTimeFromServer(fitnessActivity.activityStartTime);
        copy.activityEndTime = this.dateUtils
            .convertDateTimeFromServer(fitnessActivity.activityEndTime);
        copy.modifyTime = this.dateUtils
            .convertDateTimeFromServer(fitnessActivity.modifyTime);
        return copy;
    }

    /**
     * Convert a FitnessActivity to a JSON which can be sent to the server.
     */
    private convert(fitnessActivity: FitnessActivity): FitnessActivity {
        const copy: FitnessActivity = Object.assign({}, fitnessActivity);

        copy.signStartTime = this.dateUtils.toDate(fitnessActivity.signStartTime);

        copy.signEndTime = this.dateUtils.toDate(fitnessActivity.signEndTime);

        copy.activityStartTime = this.dateUtils.toDate(fitnessActivity.activityStartTime);

        copy.activityEndTime = this.dateUtils.toDate(fitnessActivity.activityEndTime);

        copy.modifyTime = this.dateUtils.toDate(fitnessActivity.modifyTime);
        return copy;
    }
}
