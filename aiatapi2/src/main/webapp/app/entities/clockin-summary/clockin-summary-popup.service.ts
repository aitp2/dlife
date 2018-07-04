import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ClockinSummary } from './clockin-summary.model';
import { ClockinSummaryService } from './clockin-summary.service';

@Injectable()
export class ClockinSummaryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private clockinSummaryService: ClockinSummaryService

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
                this.clockinSummaryService.find(id).subscribe((clockinSummary) => {
                    clockinSummary.lastClockInTime = this.datePipe
                        .transform(clockinSummary.lastClockInTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.clockinSummaryModalRef(component, clockinSummary);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.clockinSummaryModalRef(component, new ClockinSummary());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    clockinSummaryModalRef(component: Component, clockinSummary: ClockinSummary): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.clockinSummary = clockinSummary;
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
