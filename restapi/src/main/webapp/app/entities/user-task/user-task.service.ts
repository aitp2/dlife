import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserTask } from 'app/shared/model/user-task.model';

type EntityResponseType = HttpResponse<IUserTask>;
type EntityArrayResponseType = HttpResponse<IUserTask[]>;

@Injectable({ providedIn: 'root' })
export class UserTaskService {
    private resourceUrl = SERVER_API_URL + 'api/user-tasks';

    constructor(private http: HttpClient) {}

    create(userTask: IUserTask): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(userTask);
        return this.http
            .post<IUserTask>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(userTask: IUserTask): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(userTask);
        return this.http
            .put<IUserTask>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IUserTask>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IUserTask[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(userTask: IUserTask): IUserTask {
        const copy: IUserTask = Object.assign({}, userTask, {
            validateTo: userTask.validateTo != null && userTask.validateTo.isValid() ? userTask.validateTo.toJSON() : null,
            createTime: userTask.createTime != null && userTask.createTime.isValid() ? userTask.createTime.toJSON() : null,
            lastModifyTime: userTask.lastModifyTime != null && userTask.lastModifyTime.isValid() ? userTask.lastModifyTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.validateTo = res.body.validateTo != null ? moment(res.body.validateTo) : null;
        res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
        res.body.lastModifyTime = res.body.lastModifyTime != null ? moment(res.body.lastModifyTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((userTask: IUserTask) => {
            userTask.validateTo = userTask.validateTo != null ? moment(userTask.validateTo) : null;
            userTask.createTime = userTask.createTime != null ? moment(userTask.createTime) : null;
            userTask.lastModifyTime = userTask.lastModifyTime != null ? moment(userTask.lastModifyTime) : null;
        });
        return res;
    }
}
