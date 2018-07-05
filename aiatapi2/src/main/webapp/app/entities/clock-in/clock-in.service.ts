import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ClockIn } from './clock-in.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ClockInService {

    private resourceUrl =  SERVER_API_URL + 'api/clock-ins';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(clockIn: ClockIn): Observable<ClockIn> {
        const copy = this.convert(clockIn);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(clockIn: ClockIn): Observable<ClockIn> {
        const copy = this.convert(clockIn);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ClockIn> {
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
     * Convert a returned JSON object to ClockIn.
     */
    private convertItemFromServer(json: any): ClockIn {
        const entity: ClockIn = Object.assign(new ClockIn(), json);
        entity.punchDateTime = this.dateUtils
            .convertDateTimeFromServer(json.punchDateTime);
        return entity;
    }

    /**
     * Convert a ClockIn to a JSON which can be sent to the server.
     */
    private convert(clockIn: ClockIn): ClockIn {
        const copy: ClockIn = Object.assign({}, clockIn);

        copy.punchDateTime = this.dateUtils.toDate(clockIn.punchDateTime);
        return copy;
    }
}
