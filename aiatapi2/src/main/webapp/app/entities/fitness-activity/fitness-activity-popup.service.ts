import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { FitnessActivity } from './fitness-activity.model';
import { FitnessActivityService } from './fitness-activity.service';

@Injectable()
export class FitnessActivityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private fitnessActivityService: FitnessActivityService

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
                this.fitnessActivityService.find(id).subscribe((fitnessActivity) => {
                    fitnessActivity.signStartTime = this.datePipe
                        .transform(fitnessActivity.signStartTime, 'yyyy-MM-ddTHH:mm:ss');
                    fitnessActivity.signEndTime = this.datePipe
                        .transform(fitnessActivity.signEndTime, 'yyyy-MM-ddTHH:mm:ss');
                    fitnessActivity.activityStartTime = this.datePipe
                        .transform(fitnessActivity.activityStartTime, 'yyyy-MM-ddTHH:mm:ss');
                    fitnessActivity.activityEndTime = this.datePipe
                        .transform(fitnessActivity.activityEndTime, 'yyyy-MM-ddTHH:mm:ss');
                    fitnessActivity.modifyTime = this.datePipe
                        .transform(fitnessActivity.modifyTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.fitnessActivityModalRef(component, fitnessActivity);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.fitnessActivityModalRef(component, new FitnessActivity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    fitnessActivityModalRef(component: Component, fitnessActivity: FitnessActivity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.fitnessActivity = fitnessActivity;
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
