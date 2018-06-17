import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Follow } from './follow.model';
import { FollowService } from './follow.service';

@Component({
    selector: 'jhi-follow-detail',
    templateUrl: './follow-detail.component.html'
})
export class FollowDetailComponent implements OnInit, OnDestroy {

    follow: Follow;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private followService: FollowService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFollows();
    }

    load(id) {
        this.followService.find(id)
            .subscribe((followResponse: HttpResponse<Follow>) => {
                this.follow = followResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFollows() {
        this.eventSubscriber = this.eventManager.subscribe(
            'followListModification',
            (response) => this.load(this.follow.id)
        );
    }
}
