import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FitnessActivity } from './fitness-activity.model';
import { FitnessActivityPopupService } from './fitness-activity-popup.service';
import { FitnessActivityService } from './fitness-activity.service';

@Component({
    selector: 'jhi-fitness-activity-dialog',
    templateUrl: './fitness-activity-dialog.component.html'
})
export class FitnessActivityDialogComponent implements OnInit {

    fitnessActivity: FitnessActivity;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private fitnessActivityService: FitnessActivityService,
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
        if (this.fitnessActivity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fitnessActivityService.update(this.fitnessActivity));
        } else {
            this.subscribeToSaveResponse(
                this.fitnessActivityService.create(this.fitnessActivity));
        }
    }

    private subscribeToSaveResponse(result: Observable<FitnessActivity>) {
        result.subscribe((res: FitnessActivity) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FitnessActivity) {
        this.eventManager.broadcast({ name: 'fitnessActivityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-fitness-activity-popup',
    template: ''
})
export class FitnessActivityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fitnessActivityPopupService: FitnessActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.fitnessActivityPopupService
                    .open(FitnessActivityDialogComponent as Component, params['id']);
            } else {
                this.fitnessActivityPopupService
                    .open(FitnessActivityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
