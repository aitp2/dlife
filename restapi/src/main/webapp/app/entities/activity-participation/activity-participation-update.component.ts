import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IActivityParticipation } from 'app/shared/model/activity-participation.model';
import { ActivityParticipationService } from './activity-participation.service';
import { IFitnessActivity } from 'app/shared/model/fitness-activity.model';
import { FitnessActivityService } from 'app/entities/fitness-activity';

@Component({
    selector: 'jhi-activity-participation-update',
    templateUrl: './activity-participation-update.component.html'
})
export class ActivityParticipationUpdateComponent implements OnInit {
    private _activityParticipation: IActivityParticipation;
    isSaving: boolean;

    fitnessactivities: IFitnessActivity[];
    participationTime: string;
    latestClockinTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private activityParticipationService: ActivityParticipationService,
        private fitnessActivityService: FitnessActivityService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ activityParticipation }) => {
            this.activityParticipation = activityParticipation;
        });
        this.fitnessActivityService.query().subscribe(
            (res: HttpResponse<IFitnessActivity[]>) => {
                this.fitnessactivities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.activityParticipation.participationTime = moment(this.participationTime, DATE_TIME_FORMAT);
        this.activityParticipation.latestClockinTime = moment(this.latestClockinTime, DATE_TIME_FORMAT);
        if (this.activityParticipation.id !== undefined) {
            this.subscribeToSaveResponse(this.activityParticipationService.update(this.activityParticipation));
        } else {
            this.subscribeToSaveResponse(this.activityParticipationService.create(this.activityParticipation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IActivityParticipation>>) {
        result.subscribe(
            (res: HttpResponse<IActivityParticipation>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackFitnessActivityById(index: number, item: IFitnessActivity) {
        return item.id;
    }
    get activityParticipation() {
        return this._activityParticipation;
    }

    set activityParticipation(activityParticipation: IActivityParticipation) {
        this._activityParticipation = activityParticipation;
        this.participationTime = moment(activityParticipation.participationTime).format(DATE_TIME_FORMAT);
        this.latestClockinTime = moment(activityParticipation.latestClockinTime).format(DATE_TIME_FORMAT);
    }
}
