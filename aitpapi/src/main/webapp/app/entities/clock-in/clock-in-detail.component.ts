import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ClockIn } from './clock-in.model';
import { ClockInService } from './clock-in.service';

@Component({
    selector: 'jhi-clock-in-detail',
    templateUrl: './clock-in-detail.component.html'
})
export class ClockInDetailComponent implements OnInit, OnDestroy {

    clockIn: ClockIn;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private clockInService: ClockInService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClockIns();
    }

    load(id) {
        this.clockInService.find(id)
            .subscribe((clockInResponse: HttpResponse<ClockIn>) => {
                this.clockIn = clockInResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClockIns() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clockInListModification',
            (response) => this.load(this.clockIn.id)
        );
    }
}
