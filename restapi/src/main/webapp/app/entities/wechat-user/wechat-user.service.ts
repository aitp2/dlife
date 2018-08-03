import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWechatUser } from 'app/shared/model/wechat-user.model';

type EntityResponseType = HttpResponse<IWechatUser>;
type EntityArrayResponseType = HttpResponse<IWechatUser[]>;

@Injectable({ providedIn: 'root' })
export class WechatUserService {
    private resourceUrl = SERVER_API_URL + 'api/wechat-users';

    constructor(private http: HttpClient) {}

    create(wechatUser: IWechatUser): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(wechatUser);
        return this.http
            .post<IWechatUser>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(wechatUser: IWechatUser): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(wechatUser);
        return this.http
            .put<IWechatUser>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IWechatUser>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IWechatUser[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(wechatUser: IWechatUser): IWechatUser {
        const copy: IWechatUser = Object.assign({}, wechatUser, {
            createTime: wechatUser.createTime != null && wechatUser.createTime.isValid() ? wechatUser.createTime.toJSON() : null,
            modifyTime: wechatUser.modifyTime != null && wechatUser.modifyTime.isValid() ? wechatUser.modifyTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
        res.body.modifyTime = res.body.modifyTime != null ? moment(res.body.modifyTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((wechatUser: IWechatUser) => {
            wechatUser.createTime = wechatUser.createTime != null ? moment(wechatUser.createTime) : null;
            wechatUser.modifyTime = wechatUser.modifyTime != null ? moment(wechatUser.modifyTime) : null;
        });
        return res;
    }
}
