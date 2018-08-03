import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPinfanPics } from 'app/shared/model/pinfan-pics.model';

type EntityResponseType = HttpResponse<IPinfanPics>;
type EntityArrayResponseType = HttpResponse<IPinfanPics[]>;

@Injectable({ providedIn: 'root' })
export class PinfanPicsService {
    private resourceUrl = SERVER_API_URL + 'api/pinfan-pics';

    constructor(private http: HttpClient) {}

    create(pinfanPics: IPinfanPics): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pinfanPics);
        return this.http
            .post<IPinfanPics>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(pinfanPics: IPinfanPics): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pinfanPics);
        return this.http
            .put<IPinfanPics>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPinfanPics>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPinfanPics[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(pinfanPics: IPinfanPics): IPinfanPics {
        const copy: IPinfanPics = Object.assign({}, pinfanPics, {
            createTime: pinfanPics.createTime != null && pinfanPics.createTime.isValid() ? pinfanPics.createTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((pinfanPics: IPinfanPics) => {
            pinfanPics.createTime = pinfanPics.createTime != null ? moment(pinfanPics.createTime) : null;
        });
        return res;
    }
}
