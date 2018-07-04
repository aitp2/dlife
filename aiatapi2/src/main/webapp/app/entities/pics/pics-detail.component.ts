import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Pics } from './pics.model';
import { PicsService } from './pics.service';

@Component({
    selector: 'jhi-pics-detail',
    templateUrl: './pics-detail.component.html'
})
export class PicsDetailComponent implements OnInit, OnDestroy {

    pics: Pics;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private picsService: PicsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPics();
    }

    load(id) {
        this.picsService.find(id).subscribe((pics) => {
            this.pics = pics;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPics() {
        this.eventSubscriber = this.eventManager.subscribe(
            'picsListModification',
            (response) => this.load(this.pics.id)
        );
    }
}
