import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttendee } from 'app/shared/model/attendee.model';

@Component({
    selector: 'jhi-attendee-detail',
    templateUrl: './attendee-detail.component.html'
})
export class AttendeeDetailComponent implements OnInit {
    attendee: IAttendee;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ attendee }) => {
            this.attendee = attendee;
        });
    }

    previousState() {
        window.history.back();
    }
}
