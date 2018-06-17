import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Follow } from './follow.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Follow>;

@Injectable()
export class FollowService {

    private resourceUrl =  SERVER_API_URL + 'api/follows';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(follow: Follow): Observable<EntityResponseType> {
        const copy = this.convert(follow);
        return this.http.post<Follow>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(follow: Follow): Observable<EntityResponseType> {
        const copy = this.convert(follow);
        return this.http.put<Follow>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Follow>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Follow[]>> {
        const options = createRequestOption(req);
        return this.http.get<Follow[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Follow[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Follow = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Follow[]>): HttpResponse<Follow[]> {
        const jsonResponse: Follow[] = res.body;
        const body: Follow[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Follow.
     */
    private convertItemFromServer(follow: Follow): Follow {
        const copy: Follow = Object.assign({}, follow);
        copy.createTime = this.dateUtils
            .convertDateTimeFromServer(follow.createTime);
        copy.modifyTime = this.dateUtils
            .convertDateTimeFromServer(follow.modifyTime);
        return copy;
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
