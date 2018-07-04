import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PinfanPics } from './pinfan-pics.model';
import { PinfanPicsService } from './pinfan-pics.service';

@Component({
    selector: 'jhi-pinfan-pics-detail',
    templateUrl: './pinfan-pics-detail.component.html'
})
export class PinfanPicsDetailComponent implements OnInit, OnDestroy {

    pinfanPics: PinfanPics;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pinfanPicsService: PinfanPicsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPinfanPics();
    }

    load(id) {
        this.pinfanPicsService.find(id).subscribe((pinfanPics) => {
            this.pinfanPics = pinfanPics;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPinfanPics() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pinfanPicsListModification',
            (response) => this.load(this.pinfanPics.id)
        );
    }
}
