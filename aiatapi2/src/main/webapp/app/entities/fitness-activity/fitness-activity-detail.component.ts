import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { FitnessActivity } from './fitness-activity.model';
import { FitnessActivityService } from './fitness-activity.service';

@Component({
    selector: 'jhi-fitness-activity-detail',
    templateUrl: './fitness-activity-detail.component.html'
})
export class FitnessActivityDetailComponent implements OnInit, OnDestroy {

    fitnessActivity: FitnessActivity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private fitnessActivityService: FitnessActivityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFitnessActivities();
    }

    load(id) {
        this.fitnessActivityService.find(id).subscribe((fitnessActivity) => {
            this.fitnessActivity = fitnessActivity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFitnessActivities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'fitnessActivityListModification',
            (response) => this.load(this.fitnessActivity.id)
        );
    }
}
