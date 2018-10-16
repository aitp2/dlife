import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITaskDefine } from 'app/shared/model/task-define.model';

type EntityResponseType = HttpResponse<ITaskDefine>;
type EntityArrayResponseType = HttpResponse<ITaskDefine[]>;

@Injectable({ providedIn: 'root' })
export class TaskDefineService {
    private resourceUrl = SERVER_API_URL + 'api/task-defines';

    constructor(private http: HttpClient) {}

    create(taskDefine: ITaskDefine): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(taskDefine);
        return this.http
            .post<ITaskDefine>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(taskDefine: ITaskDefine): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(taskDefine);
        return this.http
            .put<ITaskDefine>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITaskDefine>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITaskDefine[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(taskDefine: ITaskDefine): ITaskDefine {
        const copy: ITaskDefine = Object.assign({}, taskDefine, {
            createTime: taskDefine.createTime != null && taskDefine.createTime.isValid() ? taskDefine.createTime.toJSON() : null,
            lastModifyTime:
                taskDefine.lastModifyTime != null && taskDefine.lastModifyTime.isValid() ? taskDefine.lastModifyTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
        res.body.lastModifyTime = res.body.lastModifyTime != null ? moment(res.body.lastModifyTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((taskDefine: ITaskDefine) => {
            taskDefine.createTime = taskDefine.createTime != null ? moment(taskDefine.createTime) : null;
            taskDefine.lastModifyTime = taskDefine.lastModifyTime != null ? moment(taskDefine.lastModifyTime) : null;
        });
        return res;
    }
}
