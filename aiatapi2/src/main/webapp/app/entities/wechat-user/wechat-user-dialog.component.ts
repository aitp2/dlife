import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WechatUser } from './wechat-user.model';
import { WechatUserPopupService } from './wechat-user-popup.service';
import { WechatUserService } from './wechat-user.service';

@Component({
    selector: 'jhi-wechat-user-dialog',
    templateUrl: './wechat-user-dialog.component.html'
})
export class WechatUserDialogComponent implements OnInit {

    wechatUser: WechatUser;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private wechatUserService: WechatUserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.wechatUser.id !== undefined) {
            this.subscribeToSaveResponse(
                this.wechatUserService.update(this.wechatUser));
        } else {
            this.subscribeToSaveResponse(
                this.wechatUserService.create(this.wechatUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<WechatUser>) {
        result.subscribe((res: WechatUser) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: WechatUser) {
        this.eventManager.broadcast({ name: 'wechatUserListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-wechat-user-popup',
    template: ''
})
export class WechatUserPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private wechatUserPopupService: WechatUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.wechatUserPopupService
                    .open(WechatUserDialogComponent as Component, params['id']);
            } else {
                this.wechatUserPopupService
                    .open(WechatUserDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
