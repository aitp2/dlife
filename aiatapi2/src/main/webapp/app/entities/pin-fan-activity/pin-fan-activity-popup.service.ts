import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PinFanActivity } from './pin-fan-activity.model';
import { PinFanActivityService } from './pin-fan-activity.service';

@Injectable()
export class PinFanActivityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private pinFanActivityService: PinFanActivityService

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
                this.pinFanActivityService.find(id).subscribe((pinFanActivity) => {
                    pinFanActivity.appointDatetime = this.datePipe
                        .transform(pinFanActivity.appointDatetime, 'yyyy-MM-ddTHH:mm:ss');
                    pinFanActivity.appointEndDatetime = this.datePipe
                        .transform(pinFanActivity.appointEndDatetime, 'yyyy-MM-ddTHH:mm:ss');
                    pinFanActivity.deadline = this.datePipe
                        .transform(pinFanActivity.deadline, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.pinFanActivityModalRef(component, pinFanActivity);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pinFanActivityModalRef(component, new PinFanActivity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pinFanActivityModalRef(component: Component, pinFanActivity: PinFanActivity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pinFanActivity = pinFanActivity;
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
