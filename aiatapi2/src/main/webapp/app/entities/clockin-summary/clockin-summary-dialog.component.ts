import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ClockinSummary } from './clockin-summary.model';
import { ClockinSummaryPopupService } from './clockin-summary-popup.service';
import { ClockinSummaryService } from './clockin-summary.service';

@Component({
    selector: 'jhi-clockin-summary-dialog',
    templateUrl: './clockin-summary-dialog.component.html'
})
export class ClockinSummaryDialogComponent implements OnInit {

    clockinSummary: ClockinSummary;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private clockinSummaryService: ClockinSummaryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.clockinSummary.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clockinSummaryService.update(this.clockinSummary));
        } else {
            this.subscribeToSaveResponse(
                this.clockinSummaryService.create(this.clockinSummary));
        }
    }

    private subscribeToSaveResponse(result: Observable<ClockinSummary>) {
        result.subscribe((res: ClockinSummary) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ClockinSummary) {
        this.eventManager.broadcast({ name: 'clockinSummaryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-clockin-summary-popup',
    template: ''
})
export class ClockinSummaryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clockinSummaryPopupService: ClockinSummaryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.clockinSummaryPopupService
                    .open(ClockinSummaryDialogComponent as Component, params['id']);
            } else {
                this.clockinSummaryPopupService
                    .open(ClockinSummaryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
