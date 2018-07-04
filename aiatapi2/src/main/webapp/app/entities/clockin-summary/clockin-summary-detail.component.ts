import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ClockinSummary } from './clockin-summary.model';
import { ClockinSummaryService } from './clockin-summary.service';

@Component({
    selector: 'jhi-clockin-summary-detail',
    templateUrl: './clockin-summary-detail.component.html'
})
export class ClockinSummaryDetailComponent implements OnInit, OnDestroy {

    clockinSummary: ClockinSummary;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private clockinSummaryService: ClockinSummaryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClockinSummaries();
    }

    load(id) {
        this.clockinSummaryService.find(id).subscribe((clockinSummary) => {
            this.clockinSummary = clockinSummary;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClockinSummaries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clockinSummaryListModification',
            (response) => this.load(this.clockinSummary.id)
        );
    }
}
