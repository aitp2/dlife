import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ActivityParticipation } from './activity-participation.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ActivityParticipationService {

    private resourceUrl =  SERVER_API_URL + 'api/activity-participations';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(activityParticipation: ActivityParticipation): Observable<ActivityParticipation> {
        const copy = this.convert(activityParticipation);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(activityParticipation: ActivityParticipation): Observable<ActivityParticipation> {
        const copy = this.convert(activityParticipation);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ActivityParticipation> {
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
     * Convert a returned JSON object to ActivityParticipation.
     */
    private convertItemFromServer(json: any): ActivityParticipation {
        const entity: ActivityParticipation = Object.assign(new ActivityParticipation(), json);
        entity.participationTime = this.dateUtils
            .convertDateTimeFromServer(json.participationTime);
        return entity;
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
