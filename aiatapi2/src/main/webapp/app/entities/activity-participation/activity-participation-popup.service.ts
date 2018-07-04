import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ActivityParticipation } from './activity-participation.model';
import { ActivityParticipationService } from './activity-participation.service';

@Injectable()
export class ActivityParticipationPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private activityParticipationService: ActivityParticipationService

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
                this.activityParticipationService.find(id).subscribe((activityParticipation) => {
                    activityParticipation.participationTime = this.datePipe
                        .transform(activityParticipation.participationTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.activityParticipationModalRef(component, activityParticipation);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.activityParticipationModalRef(component, new ActivityParticipation());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    activityParticipationModalRef(component: Component, activityParticipation: ActivityParticipation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.activityParticipation = activityParticipation;
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
