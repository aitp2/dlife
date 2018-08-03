import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommentPic } from 'app/shared/model/comment-pic.model';

type EntityResponseType = HttpResponse<ICommentPic>;
type EntityArrayResponseType = HttpResponse<ICommentPic[]>;

@Injectable({ providedIn: 'root' })
export class CommentPicService {
    private resourceUrl = SERVER_API_URL + 'api/comment-pics';

    constructor(private http: HttpClient) {}

    create(commentPic: ICommentPic): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commentPic);
        return this.http
            .post<ICommentPic>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(commentPic: ICommentPic): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commentPic);
        return this.http
            .put<ICommentPic>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICommentPic>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommentPic[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(commentPic: ICommentPic): ICommentPic {
        const copy: ICommentPic = Object.assign({}, commentPic, {
            createTime: commentPic.createTime != null && commentPic.createTime.isValid() ? commentPic.createTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((commentPic: ICommentPic) => {
            commentPic.createTime = commentPic.createTime != null ? moment(commentPic.createTime) : null;
        });
        return res;
    }
}
