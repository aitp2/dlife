import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Pics } from './pics.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PicsService {

    private resourceUrl =  SERVER_API_URL + 'api/pics';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(pics: Pics): Observable<Pics> {
        const copy = this.convert(pics);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(pics: Pics): Observable<Pics> {
        const copy = this.convert(pics);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Pics> {
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
     * Convert a returned JSON object to Pics.
     */
    private convertItemFromServer(json: any): Pics {
        const entity: Pics = Object.assign(new Pics(), json);
        entity.createTime = this.dateUtils
            .convertDateTimeFromServer(json.createTime);
        return entity;
    }

    /**
     * Convert a Pics to a JSON which can be sent to the server.
     */
    private convert(pics: Pics): Pics {
        const copy: Pics = Object.assign({}, pics);

        copy.createTime = this.dateUtils.toDate(pics.createTime);
        return copy;
    }
}
