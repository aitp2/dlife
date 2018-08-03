import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEventMessage } from 'app/shared/model/event-message.model';
import { EventMessageService } from './event-message.service';

@Component({
    selector: 'jhi-event-message-update',
    templateUrl: './event-message-update.component.html'
})
export class EventMessageUpdateComponent implements OnInit {
    private _eventMessage: IEventMessage;
    isSaving: boolean;
    createTime: string;

    constructor(private eventMessageService: EventMessageService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ eventMessage }) => {
            this.eventMessage = eventMessage;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.eventMessage.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        if (this.eventMessage.id !== undefined) {
            this.subscribeToSaveResponse(this.eventMessageService.update(this.eventMessage));
        } else {
            this.subscribeToSaveResponse(this.eventMessageService.create(this.eventMessage));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEventMessage>>) {
        result.subscribe((res: HttpResponse<IEventMessage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get eventMessage() {
        return this._eventMessage;
    }

    set eventMessage(eventMessage: IEventMessage) {
        this._eventMessage = eventMessage;
        this.createTime = moment(eventMessage.createTime).format(DATE_TIME_FORMAT);
    }
}
