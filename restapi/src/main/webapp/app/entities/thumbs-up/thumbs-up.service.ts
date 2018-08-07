import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IThumbsUp } from 'app/shared/model/thumbs-up.model';

type EntityResponseType = HttpResponse<IThumbsUp>;
type EntityArrayResponseType = HttpResponse<IThumbsUp[]>;

@Injectable({ providedIn: 'root' })
export class ThumbsUpService {
    private resourceUrl = SERVER_API_URL + 'api/thumbs-ups';

    constructor(private http: HttpClient) {}

    create(thumbsUp: IThumbsUp): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(thumbsUp);
        return this.http
            .post<IThumbsUp>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(thumbsUp: IThumbsUp): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(thumbsUp);
        return this.http
            .put<IThumbsUp>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IThumbsUp>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IThumbsUp[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(thumbsUp: IThumbsUp): IThumbsUp {
        const copy: IThumbsUp = Object.assign({}, thumbsUp, {
            createTime: thumbsUp.createTime != null && thumbsUp.createTime.isValid() ? thumbsUp.createTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((thumbsUp: IThumbsUp) => {
            thumbsUp.createTime = thumbsUp.createTime != null ? moment(thumbsUp.createTime) : null;
        });
        return res;
    }
}
