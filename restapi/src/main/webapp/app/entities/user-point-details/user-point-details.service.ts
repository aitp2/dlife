import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserPointDetails } from 'app/shared/model/user-point-details.model';

type EntityResponseType = HttpResponse<IUserPointDetails>;
type EntityArrayResponseType = HttpResponse<IUserPointDetails[]>;

@Injectable({ providedIn: 'root' })
export class UserPointDetailsService {
    private resourceUrl = SERVER_API_URL + 'api/user-point-details';

    constructor(private http: HttpClient) {}

    create(userPointDetails: IUserPointDetails): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(userPointDetails);
        return this.http
            .post<IUserPointDetails>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(userPointDetails: IUserPointDetails): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(userPointDetails);
        return this.http
            .put<IUserPointDetails>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IUserPointDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IUserPointDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(userPointDetails: IUserPointDetails): IUserPointDetails {
        const copy: IUserPointDetails = Object.assign({}, userPointDetails, {
            applyTime:
                userPointDetails.applyTime != null && userPointDetails.applyTime.isValid() ? userPointDetails.applyTime.toJSON() : null,
            createTime:
                userPointDetails.createTime != null && userPointDetails.createTime.isValid() ? userPointDetails.createTime.toJSON() : null,
            lastModifyTime:
                userPointDetails.lastModifyTime != null && userPointDetails.lastModifyTime.isValid()
                    ? userPointDetails.lastModifyTime.toJSON()
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.applyTime = res.body.applyTime != null ? moment(res.body.applyTime) : null;
        res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
        res.body.lastModifyTime = res.body.lastModifyTime != null ? moment(res.body.lastModifyTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((userPointDetails: IUserPointDetails) => {
            userPointDetails.applyTime = userPointDetails.applyTime != null ? moment(userPointDetails.applyTime) : null;
            userPointDetails.createTime = userPointDetails.createTime != null ? moment(userPointDetails.createTime) : null;
            userPointDetails.lastModifyTime = userPointDetails.lastModifyTime != null ? moment(userPointDetails.lastModifyTime) : null;
        });
        return res;
    }
}
