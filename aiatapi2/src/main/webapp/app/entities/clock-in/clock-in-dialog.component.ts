import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ClockIn } from './clock-in.model';
import { ClockInPopupService } from './clock-in-popup.service';
import { ClockInService } from './clock-in.service';
import { ActivityParticipation, ActivityParticipationService } from '../activity-participation';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-clock-in-dialog',
    templateUrl: './clock-in-dialog.component.html'
})
export class ClockInDialogComponent implements OnInit {

    clockIn: ClockIn;
    isSaving: boolean;

    activityparticipations: ActivityParticipation[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private clockInService: ClockInService,
        private activityParticipationService: ActivityParticipationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.activityParticipationService.query()
            .subscribe((res: ResponseWrapper) => { this.activityparticipations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.clockIn.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clockInService.update(this.clockIn));
        } else {
            this.subscribeToSaveResponse(
                this.clockInService.create(this.clockIn));
        }
    }

    private subscribeToSaveResponse(result: Observable<ClockIn>) {
        result.subscribe((res: ClockIn) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ClockIn) {
        this.eventManager.broadcast({ name: 'clockInListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackActivityParticipationById(index: number, item: ActivityParticipation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-clock-in-popup',
    template: ''
})
export class ClockInPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clockInPopupService: ClockInPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.clockInPopupService
                    .open(ClockInDialogComponent as Component, params['id']);
            } else {
                this.clockInPopupService
                    .open(ClockInDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
