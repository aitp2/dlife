import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Evaluate } from './evaluate.model';
import { EvaluateService } from './evaluate.service';

@Component({
    selector: 'jhi-evaluate-detail',
    templateUrl: './evaluate-detail.component.html'
})
export class EvaluateDetailComponent implements OnInit, OnDestroy {

    evaluate: Evaluate;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private evaluateService: EvaluateService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEvaluates();
    }

    load(id) {
        this.evaluateService.find(id)
            .subscribe((evaluateResponse: HttpResponse<Evaluate>) => {
                this.evaluate = evaluateResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEvaluates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'evaluateListModification',
            (response) => this.load(this.evaluate.id)
        );
    }
}
