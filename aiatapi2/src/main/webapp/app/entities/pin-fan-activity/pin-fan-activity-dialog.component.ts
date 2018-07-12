import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PinFanActivity } from './pin-fan-activity.model';
import { PinFanActivityPopupService } from './pin-fan-activity-popup.service';
import { PinFanActivityService } from './pin-fan-activity.service';

@Component({
    selector: 'jhi-pin-fan-activity-dialog',
    templateUrl: './pin-fan-activity-dialog.component.html'
})
export class PinFanActivityDialogComponent implements OnInit {

    pinFanActivity: PinFanActivity;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private pinFanActivityService: PinFanActivityService,
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
        if (this.pinFanActivity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pinFanActivityService.update(this.pinFanActivity));
        } else {
            this.subscribeToSaveResponse(
                this.pinFanActivityService.create(this.pinFanActivity));
        }
    }

    private subscribeToSaveResponse(result: Observable<PinFanActivity>) {
        result.subscribe((res: PinFanActivity) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PinFanActivity) {
        this.eventManager.broadcast({ name: 'pinFanActivityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-pin-fan-activity-popup',
    template: ''
})
export class PinFanActivityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pinFanActivityPopupService: PinFanActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pinFanActivityPopupService
                    .open(PinFanActivityDialogComponent as Component, params['id']);
            } else {
                this.pinFanActivityPopupService
                    .open(PinFanActivityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
