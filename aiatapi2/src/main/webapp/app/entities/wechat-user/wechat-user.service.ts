import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { WechatUser } from './wechat-user.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class WechatUserService {

    private resourceUrl =  SERVER_API_URL + 'api/wechat-users';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(wechatUser: WechatUser): Observable<WechatUser> {
        const copy = this.convert(wechatUser);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(wechatUser: WechatUser): Observable<WechatUser> {
        const copy = this.convert(wechatUser);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<WechatUser> {
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
     * Convert a returned JSON object to WechatUser.
     */
    private convertItemFromServer(json: any): WechatUser {
        const entity: WechatUser = Object.assign(new WechatUser(), json);
        entity.createTime = this.dateUtils
            .convertDateTimeFromServer(json.createTime);
        entity.modifyTime = this.dateUtils
            .convertDateTimeFromServer(json.modifyTime);
        return entity;
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
