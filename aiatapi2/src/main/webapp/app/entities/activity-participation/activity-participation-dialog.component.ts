import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ActivityParticipation } from './activity-participation.model';
import { ActivityParticipationPopupService } from './activity-participation-popup.service';
import { ActivityParticipationService } from './activity-participation.service';
import { FitnessActivity, FitnessActivityService } from '../fitness-activity';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-activity-participation-dialog',
    templateUrl: './activity-participation-dialog.component.html'
})
export class ActivityParticipationDialogComponent implements OnInit {

    activityParticipation: ActivityParticipation;
    isSaving: boolean;

    fitnessactivities: FitnessActivity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private activityParticipationService: ActivityParticipationService,
        private fitnessActivityService: FitnessActivityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.fitnessActivityService.query()
            .subscribe((res: ResponseWrapper) => { this.fitnessactivities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.activityParticipation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.activityParticipationService.update(this.activityParticipation));
        } else {
            this.subscribeToSaveResponse(
                this.activityParticipationService.create(this.activityParticipation));
        }
    }

    private subscribeToSaveResponse(result: Observable<ActivityParticipation>) {
        result.subscribe((res: ActivityParticipation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ActivityParticipation) {
        this.eventManager.broadcast({ name: 'activityParticipationListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-activity-participation-popup',
    template: ''
})
export class ActivityParticipationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private activityParticipationPopupService: ActivityParticipationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.activityParticipationPopupService
                    .open(ActivityParticipationDialogComponent as Component, params['id']);
            } else {
                this.activityParticipationPopupService
                    .open(ActivityParticipationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
