import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPics } from 'app/shared/model/pics.model';

type EntityResponseType = HttpResponse<IPics>;
type EntityArrayResponseType = HttpResponse<IPics[]>;

@Injectable({ providedIn: 'root' })
export class PicsService {
    private resourceUrl = SERVER_API_URL + 'api/pics';

    constructor(private http: HttpClient) {}

    create(pics: IPics): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pics);
        return this.http
            .post<IPics>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(pics: IPics): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pics);
        return this.http
            .put<IPics>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPics>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPics[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(pics: IPics): IPics {
        const copy: IPics = Object.assign({}, pics, {
            createTime: pics.createTime != null && pics.createTime.isValid() ? pics.createTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((pics: IPics) => {
            pics.createTime = pics.createTime != null ? moment(pics.createTime) : null;
        });
        return res;
    }
}
