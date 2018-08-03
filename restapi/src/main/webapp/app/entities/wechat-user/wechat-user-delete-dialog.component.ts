import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWechatUser } from 'app/shared/model/wechat-user.model';
import { WechatUserService } from './wechat-user.service';

@Component({
    selector: 'jhi-wechat-user-delete-dialog',
    templateUrl: './wechat-user-delete-dialog.component.html'
})
export class WechatUserDeleteDialogComponent {
    wechatUser: IWechatUser;

    constructor(private wechatUserService: WechatUserService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.wechatUserService.delete(id).subscribe(response => {
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
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ wechatUser }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WechatUserDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.wechatUser = wechatUser;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
