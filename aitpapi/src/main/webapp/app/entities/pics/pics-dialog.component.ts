import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Pics } from './pics.model';
import { PicsPopupService } from './pics-popup.service';
import { PicsService } from './pics.service';
import { FitnessActivity, FitnessActivityService } from '../fitness-activity';
import { ClockIn, ClockInService } from '../clock-in';

@Component({
    selector: 'jhi-pics-dialog',
    templateUrl: './pics-dialog.component.html'
})
export class PicsDialogComponent implements OnInit {

    pics: Pics;
    isSaving: boolean;

    fitnessactivities: FitnessActivity[];

    clockins: ClockIn[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private picsService: PicsService,
        private fitnessActivityService: FitnessActivityService,
        private clockInService: ClockInService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.fitnessActivityService.query()
            .subscribe((res: HttpResponse<FitnessActivity[]>) => { this.fitnessactivities = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.clockInService.query()
            .subscribe((res: HttpResponse<ClockIn[]>) => { this.clockins = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pics.id !== undefined) {
            this.subscribeToSaveResponse(
                this.picsService.update(this.pics));
        } else {
            this.subscribeToSaveResponse(
                this.picsService.create(this.pics));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Pics>>) {
        result.subscribe((res: HttpResponse<Pics>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Pics) {
        this.eventManager.broadcast({ name: 'picsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackFitnessActivityById(index: number, item: FitnessActivity) {
        return item.id;
    }

    trackClockInById(index: number, item: ClockIn) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pics-popup',
    template: ''
})
export class PicsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private picsPopupService: PicsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.picsPopupService
                    .open(PicsDialogComponent as Component, params['id']);
            } else {
                this.picsPopupService
                    .open(PicsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
