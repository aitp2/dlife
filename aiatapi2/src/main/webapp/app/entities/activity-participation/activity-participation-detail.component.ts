import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActivityParticipation } from 'app/shared/model/activity-participation.model';

@Component({
    selector: 'jhi-activity-participation-detail',
    templateUrl: './activity-participation-detail.component.html'
})
export class ActivityParticipationDetailComponent implements OnInit {
    activityParticipation: IActivityParticipation;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ activityParticipation }) => {
            this.activityParticipation = activityParticipation;
        });
    }

    previousState() {
        window.history.back();
    }
}
