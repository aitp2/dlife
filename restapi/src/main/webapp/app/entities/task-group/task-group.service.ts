import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITaskGroup } from 'app/shared/model/task-group.model';

type EntityResponseType = HttpResponse<ITaskGroup>;
type EntityArrayResponseType = HttpResponse<ITaskGroup[]>;

@Injectable({ providedIn: 'root' })
export class TaskGroupService {
    private resourceUrl = SERVER_API_URL + 'api/task-groups';

    constructor(private http: HttpClient) {}

    create(taskGroup: ITaskGroup): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(taskGroup);
        return this.http
            .post<ITaskGroup>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(taskGroup: ITaskGroup): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(taskGroup);
        return this.http
            .put<ITaskGroup>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITaskGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITaskGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(taskGroup: ITaskGroup): ITaskGroup {
        const copy: ITaskGroup = Object.assign({}, taskGroup, {
            createTime: taskGroup.createTime != null && taskGroup.createTime.isValid() ? taskGroup.createTime.toJSON() : null,
            lastModifyTime:
                taskGroup.lastModifyTime != null && taskGroup.lastModifyTime.isValid() ? taskGroup.lastModifyTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
        res.body.lastModifyTime = res.body.lastModifyTime != null ? moment(res.body.lastModifyTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((taskGroup: ITaskGroup) => {
            taskGroup.createTime = taskGroup.createTime != null ? moment(taskGroup.createTime) : null;
            taskGroup.lastModifyTime = taskGroup.lastModifyTime != null ? moment(taskGroup.lastModifyTime) : null;
        });
        return res;
    }
}
