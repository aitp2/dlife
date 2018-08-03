import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuestionPic } from 'app/shared/model/question-pic.model';

type EntityResponseType = HttpResponse<IQuestionPic>;
type EntityArrayResponseType = HttpResponse<IQuestionPic[]>;

@Injectable({ providedIn: 'root' })
export class QuestionPicService {
    private resourceUrl = SERVER_API_URL + 'api/question-pics';

    constructor(private http: HttpClient) {}

    create(questionPic: IQuestionPic): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(questionPic);
        return this.http
            .post<IQuestionPic>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(questionPic: IQuestionPic): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(questionPic);
        return this.http
            .put<IQuestionPic>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IQuestionPic>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuestionPic[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(questionPic: IQuestionPic): IQuestionPic {
        const copy: IQuestionPic = Object.assign({}, questionPic, {
            createTime: questionPic.createTime != null && questionPic.createTime.isValid() ? questionPic.createTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((questionPic: IQuestionPic) => {
            questionPic.createTime = questionPic.createTime != null ? moment(questionPic.createTime) : null;
        });
        return res;
    }
}
