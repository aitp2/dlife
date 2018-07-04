import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Follow } from './follow.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FollowService {

    private resourceUrl =  SERVER_API_URL + 'api/follows';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(follow: Follow): Observable<Follow> {
        const copy = this.convert(follow);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(follow: Follow): Observable<Follow> {
        const copy = this.convert(follow);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Follow> {
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
     * Convert a returned JSON object to Follow.
     */
    private convertItemFromServer(json: any): Follow {
        const entity: Follow = Object.assign(new Follow(), json);
        entity.createTime = this.dateUtils
            .convertDateTimeFromServer(json.createTime);
        entity.modifyTime = this.dateUtils
            .convertDateTimeFromServer(json.modifyTime);
        return entity;
    }

    /**
     * Convert a Follow to a JSON which can be sent to the server.
     */
    private convert(follow: Follow): Follow {
        const copy: Follow = Object.assign({}, follow);

        copy.createTime = this.dateUtils.toDate(follow.createTime);

        copy.modifyTime = this.dateUtils.toDate(follow.modifyTime);
        return copy;
    }
}
