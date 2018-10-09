import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISystemTotalPoints } from 'app/shared/model/system-total-points.model';

type EntityResponseType = HttpResponse<ISystemTotalPoints>;
type EntityArrayResponseType = HttpResponse<ISystemTotalPoints[]>;

@Injectable({ providedIn: 'root' })
export class SystemTotalPointsService {
    private resourceUrl = SERVER_API_URL + 'api/system-total-points';

    constructor(private http: HttpClient) {}

    create(systemTotalPoints: ISystemTotalPoints): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(systemTotalPoints);
        return this.http
            .post<ISystemTotalPoints>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(systemTotalPoints: ISystemTotalPoints): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(systemTotalPoints);
        return this.http
            .put<ISystemTotalPoints>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISystemTotalPoints>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISystemTotalPoints[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(systemTotalPoints: ISystemTotalPoints): ISystemTotalPoints {
        const copy: ISystemTotalPoints = Object.assign({}, systemTotalPoints, {
            createTime:
                systemTotalPoints.createTime != null && systemTotalPoints.createTime.isValid()
                    ? systemTotalPoints.createTime.toJSON()
                    : null,
            lastModifyTime:
                systemTotalPoints.lastModifyTime != null && systemTotalPoints.lastModifyTime.isValid()
                    ? systemTotalPoints.lastModifyTime.toJSON()
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
        res.body.lastModifyTime = res.body.lastModifyTime != null ? moment(res.body.lastModifyTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((systemTotalPoints: ISystemTotalPoints) => {
            systemTotalPoints.createTime = systemTotalPoints.createTime != null ? moment(systemTotalPoints.createTime) : null;
            systemTotalPoints.lastModifyTime = systemTotalPoints.lastModifyTime != null ? moment(systemTotalPoints.lastModifyTime) : null;
        });
        return res;
    }
}
