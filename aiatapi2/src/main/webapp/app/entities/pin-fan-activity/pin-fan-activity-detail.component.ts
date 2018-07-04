import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PinFanActivity } from './pin-fan-activity.model';
import { PinFanActivityService } from './pin-fan-activity.service';

@Component({
    selector: 'jhi-pin-fan-activity-detail',
    templateUrl: './pin-fan-activity-detail.component.html'
})
export class PinFanActivityDetailComponent implements OnInit, OnDestroy {

    pinFanActivity: PinFanActivity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pinFanActivityService: PinFanActivityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPinFanActivities();
    }

    load(id) {
        this.pinFanActivityService.find(id).subscribe((pinFanActivity) => {
            this.pinFanActivity = pinFanActivity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPinFanActivities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pinFanActivityListModification',
            (response) => this.load(this.pinFanActivity.id)
        );
    }
}
