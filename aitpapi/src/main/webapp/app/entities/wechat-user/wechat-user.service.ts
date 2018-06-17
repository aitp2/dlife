import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { WechatUser } from './wechat-user.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<WechatUser>;

@Injectable()
export class WechatUserService {

    private resourceUrl =  SERVER_API_URL + 'api/wechat-users';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(wechatUser: WechatUser): Observable<EntityResponseType> {
        const copy = this.convert(wechatUser);
        return this.http.post<WechatUser>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(wechatUser: WechatUser): Observable<EntityResponseType> {
        const copy = this.convert(wechatUser);
        return this.http.put<WechatUser>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<WechatUser>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<WechatUser[]>> {
        const options = createRequestOption(req);
        return this.http.get<WechatUser[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<WechatUser[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: WechatUser = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<WechatUser[]>): HttpResponse<WechatUser[]> {
        const jsonResponse: WechatUser[] = res.body;
        const body: WechatUser[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to WechatUser.
     */
    private convertItemFromServer(wechatUser: WechatUser): WechatUser {
        const copy: WechatUser = Object.assign({}, wechatUser);
        copy.createTime = this.dateUtils
            .convertDateTimeFromServer(wechatUser.createTime);
        copy.modifyTime = this.dateUtils
            .convertDateTimeFromServer(wechatUser.modifyTime);
        return copy;
    }

    /**
     * Convert a WechatUser to a JSON which can be sent to the server.
     */
    private convert(wechatUser: WechatUser): WechatUser {
        const copy: WechatUser = Object.assign({}, wechatUser);

        copy.createTime = this.dateUtils.toDate(wechatUser.createTime);

        copy.modifyTime = this.dateUtils.toDate(wechatUser.modifyTime);
        return copy;
    }
}
