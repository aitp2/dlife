import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEventMessage } from 'app/shared/model/event-message.model';

type EntityResponseType = HttpResponse<IEventMessage>;
type EntityArrayResponseType = HttpResponse<IEventMessage[]>;

@Injectable({ providedIn: 'root' })
export class EventMessageService {
    private resourceUrl = SERVER_API_URL + 'api/event-messages';

    constructor(private http: HttpClient) {}

    create(eventMessage: IEventMessage): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(eventMessage);
        return this.http
            .post<IEventMessage>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(eventMessage: IEventMessage): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(eventMessage);
        return this.http
            .put<IEventMessage>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEventMessage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEventMessage[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(eventMessage: IEventMessage): IEventMessage {
        const copy: IEventMessage = Object.assign({}, eventMessage, {
            createTime: eventMessage.createTime != null && eventMessage.createTime.isValid() ? eventMessage.createTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((eventMessage: IEventMessage) => {
            eventMessage.createTime = eventMessage.createTime != null ? moment(eventMessage.createTime) : null;
        });
        return res;
    }
}
