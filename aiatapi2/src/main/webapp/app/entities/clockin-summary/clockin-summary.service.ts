import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ClockinSummary } from './clockin-summary.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ClockinSummaryService {

    private resourceUrl =  SERVER_API_URL + 'api/clockin-summaries';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(clockinSummary: ClockinSummary): Observable<ClockinSummary> {
        const copy = this.convert(clockinSummary);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(clockinSummary: ClockinSummary): Observable<ClockinSummary> {
        const copy = this.convert(clockinSummary);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ClockinSummary> {
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
     * Convert a returned JSON object to ClockinSummary.
     */
    private convertItemFromServer(json: any): ClockinSummary {
        const entity: ClockinSummary = Object.assign(new ClockinSummary(), json);
        entity.lastClockInTime = this.dateUtils
            .convertDateTimeFromServer(json.lastClockInTime);
        return entity;
    }

    /**
     * Convert a ClockinSummary to a JSON which can be sent to the server.
     */
    private convert(clockinSummary: ClockinSummary): ClockinSummary {
        const copy: ClockinSummary = Object.assign({}, clockinSummary);

        copy.lastClockInTime = this.dateUtils.toDate(clockinSummary.lastClockInTime);
        return copy;
    }
}
