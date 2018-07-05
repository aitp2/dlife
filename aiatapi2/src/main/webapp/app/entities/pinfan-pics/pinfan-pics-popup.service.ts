import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PinfanPics } from './pinfan-pics.model';
import { PinfanPicsService } from './pinfan-pics.service';

@Injectable()
export class PinfanPicsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private pinfanPicsService: PinfanPicsService

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
                this.pinfanPicsService.find(id).subscribe((pinfanPics) => {
                    pinfanPics.createTime = this.datePipe
                        .transform(pinfanPics.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.pinfanPicsModalRef(component, pinfanPics);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pinfanPicsModalRef(component, new PinfanPics());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pinfanPicsModalRef(component: Component, pinfanPics: PinfanPics): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pinfanPics = pinfanPics;
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
