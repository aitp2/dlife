import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventMessage } from 'app/shared/model/event-message.model';

@Component({
    selector: 'jhi-event-message-detail',
    templateUrl: './event-message-detail.component.html'
})
export class EventMessageDetailComponent implements OnInit {
    eventMessage: IEventMessage;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ eventMessage }) => {
            this.eventMessage = eventMessage;
        });
    }

    previousState() {
        window.history.back();
    }
}
