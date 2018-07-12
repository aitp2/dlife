import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { FitnessActivity } from './fitness-activity.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FitnessActivityService {

    private resourceUrl =  SERVER_API_URL + 'api/fitness-activities';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(fitnessActivity: FitnessActivity): Observable<FitnessActivity> {
        const copy = this.convert(fitnessActivity);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(fitnessActivity: FitnessActivity): Observable<FitnessActivity> {
        const copy = this.convert(fitnessActivity);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<FitnessActivity> {
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
     * Convert a returned JSON object to FitnessActivity.
     */
    private convertItemFromServer(json: any): FitnessActivity {
        const entity: FitnessActivity = Object.assign(new FitnessActivity(), json);
        entity.signStartTime = this.dateUtils
            .convertDateTimeFromServer(json.signStartTime);
        entity.signEndTime = this.dateUtils
            .convertDateTimeFromServer(json.signEndTime);
        entity.activityStartTime = this.dateUtils
            .convertDateTimeFromServer(json.activityStartTime);
        entity.activityEndTime = this.dateUtils
            .convertDateTimeFromServer(json.activityEndTime);
        entity.modifyTime = this.dateUtils
            .convertDateTimeFromServer(json.modifyTime);
        return entity;
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
