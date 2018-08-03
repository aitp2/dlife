import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClockinSummary } from 'app/shared/model/clockin-summary.model';
import { ClockinSummaryService } from './clockin-summary.service';

@Component({
    selector: 'jhi-clockin-summary-delete-dialog',
    templateUrl: './clockin-summary-delete-dialog.component.html'
})
export class ClockinSummaryDeleteDialogComponent {
    clockinSummary: IClockinSummary;

    constructor(
        private clockinSummaryService: ClockinSummaryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clockinSummaryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'clockinSummaryListModification',
                content: 'Deleted an clockinSummary'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-clockin-summary-delete-popup',
    template: ''
})
export class ClockinSummaryDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clockinSummary }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ClockinSummaryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.clockinSummary = clockinSummary;
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
