import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ActivityParticipation } from './activity-participation.model';
import { ActivityParticipationService } from './activity-participation.service';

@Component({
    selector: 'jhi-activity-participation-detail',
    templateUrl: './activity-participation-detail.component.html'
})
export class ActivityParticipationDetailComponent implements OnInit, OnDestroy {

    activityParticipation: ActivityParticipation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private activityParticipationService: ActivityParticipationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInActivityParticipations();
    }

    load(id) {
        this.activityParticipationService.find(id).subscribe((activityParticipation) => {
            this.activityParticipation = activityParticipation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInActivityParticipations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'activityParticipationListModification',
            (response) => this.load(this.activityParticipation.id)
        );
    }
}
