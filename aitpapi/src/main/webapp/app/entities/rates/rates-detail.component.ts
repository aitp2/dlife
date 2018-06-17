import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Rates } from './rates.model';
import { RatesService } from './rates.service';

@Component({
    selector: 'jhi-rates-detail',
    templateUrl: './rates-detail.component.html'
})
export class RatesDetailComponent implements OnInit, OnDestroy {

    rates: Rates;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ratesService: RatesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRates();
    }

    load(id) {
        this.ratesService.find(id)
            .subscribe((ratesResponse: HttpResponse<Rates>) => {
                this.rates = ratesResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ratesListModification',
            (response) => this.load(this.rates.id)
        );
    }
}
