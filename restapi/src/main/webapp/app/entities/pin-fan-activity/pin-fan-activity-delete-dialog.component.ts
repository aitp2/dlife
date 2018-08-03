import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPinFanActivity } from 'app/shared/model/pin-fan-activity.model';
import { PinFanActivityService } from './pin-fan-activity.service';

@Component({
    selector: 'jhi-pin-fan-activity-delete-dialog',
    templateUrl: './pin-fan-activity-delete-dialog.component.html'
})
export class PinFanActivityDeleteDialogComponent {
    pinFanActivity: IPinFanActivity;

    constructor(
        private pinFanActivityService: PinFanActivityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pinFanActivityService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pinFanActivityListModification',
                content: 'Deleted an pinFanActivity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pin-fan-activity-delete-popup',
    template: ''
})
export class PinFanActivityDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pinFanActivity }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PinFanActivityDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.pinFanActivity = pinFanActivity;
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
