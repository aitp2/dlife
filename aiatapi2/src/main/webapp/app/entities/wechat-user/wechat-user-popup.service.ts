import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { WechatUser } from './wechat-user.model';
import { WechatUserService } from './wechat-user.service';

@Injectable()
export class WechatUserPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private wechatUserService: WechatUserService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.wechatUserService.find(id).subscribe((wechatUser) => {
                    wechatUser.createTime = this.datePipe
                        .transform(wechatUser.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    wechatUser.modifyTime = this.datePipe
                        .transform(wechatUser.modifyTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.wechatUserModalRef(component, wechatUser);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.wechatUserModalRef(component, new WechatUser());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    wechatUserModalRef(component: Component, wechatUser: WechatUser): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.wechatUser = wechatUser;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
