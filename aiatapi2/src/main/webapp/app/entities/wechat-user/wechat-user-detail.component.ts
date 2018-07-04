import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { WechatUser } from './wechat-user.model';
import { WechatUserService } from './wechat-user.service';

@Component({
    selector: 'jhi-wechat-user-detail',
    templateUrl: './wechat-user-detail.component.html'
})
export class WechatUserDetailComponent implements OnInit, OnDestroy {

    wechatUser: WechatUser;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private wechatUserService: WechatUserService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWechatUsers();
    }

    load(id) {
        this.wechatUserService.find(id).subscribe((wechatUser) => {
            this.wechatUser = wechatUser;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWechatUsers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'wechatUserListModification',
            (response) => this.load(this.wechatUser.id)
        );
    }
}
