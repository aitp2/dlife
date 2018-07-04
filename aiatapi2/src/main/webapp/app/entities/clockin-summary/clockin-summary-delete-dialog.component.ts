import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ClockinSummary } from './clockin-summary.model';
import { ClockinSummaryPopupService } from './clockin-summary-popup.service';
import { ClockinSummaryService } from './clockin-summary.service';

@Component({
    selector: 'jhi-clockin-summary-delete-dialog',
    templateUrl: './clockin-summary-delete-dialog.component.html'
})
export class ClockinSummaryDeleteDialogComponent {

    clockinSummary: ClockinSummary;

    constructor(
        private clockinSummaryService: ClockinSummaryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clockinSummaryService.delete(id).subscribe((response) => {
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

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clockinSummaryPopupService: ClockinSummaryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.clockinSummaryPopupService
                .open(ClockinSummaryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
