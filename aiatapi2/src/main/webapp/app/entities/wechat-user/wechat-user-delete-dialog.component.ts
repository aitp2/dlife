import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WechatUser } from './wechat-user.model';
import { WechatUserPopupService } from './wechat-user-popup.service';
import { WechatUserService } from './wechat-user.service';

@Component({
    selector: 'jhi-wechat-user-delete-dialog',
    templateUrl: './wechat-user-delete-dialog.component.html'
})
export class WechatUserDeleteDialogComponent {

    wechatUser: WechatUser;

    constructor(
        private wechatUserService: WechatUserService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.wechatUserService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'wechatUserListModification',
                content: 'Deleted an wechatUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wechat-user-delete-popup',
    template: ''
})
export class WechatUserDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private wechatUserPopupService: WechatUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.wechatUserPopupService
                .open(WechatUserDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
