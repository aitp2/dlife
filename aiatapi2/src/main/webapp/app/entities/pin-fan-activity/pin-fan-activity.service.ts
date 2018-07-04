import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PinFanActivity } from './pin-fan-activity.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PinFanActivityService {

    private resourceUrl =  SERVER_API_URL + 'api/pin-fan-activities';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(pinFanActivity: PinFanActivity): Observable<PinFanActivity> {
        const copy = this.convert(pinFanActivity);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(pinFanActivity: PinFanActivity): Observable<PinFanActivity> {
        const copy = this.convert(pinFanActivity);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PinFanActivity> {
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
     * Convert a returned JSON object to PinFanActivity.
     */
    private convertItemFromServer(json: any): PinFanActivity {
        const entity: PinFanActivity = Object.assign(new PinFanActivity(), json);
        entity.appointDatetime = this.dateUtils
            .convertDateTimeFromServer(json.appointDatetime);
        entity.appointEndDatetime = this.dateUtils
            .convertDateTimeFromServer(json.appointEndDatetime);
        entity.deadline = this.dateUtils
            .convertDateTimeFromServer(json.deadline);
        return entity;
    }

    /**
     * Convert a PinFanActivity to a JSON which can be sent to the server.
     */
    private convert(pinFanActivity: PinFanActivity): PinFanActivity {
        const copy: PinFanActivity = Object.assign({}, pinFanActivity);

        copy.appointDatetime = this.dateUtils.toDate(pinFanActivity.appointDatetime);

        copy.appointEndDatetime = this.dateUtils.toDate(pinFanActivity.appointEndDatetime);

        copy.deadline = this.dateUtils.toDate(pinFanActivity.deadline);
        return copy;
    }
}
