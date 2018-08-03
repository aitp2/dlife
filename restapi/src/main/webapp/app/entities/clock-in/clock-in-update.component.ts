import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IClockIn } from 'app/shared/model/clock-in.model';
import { ClockInService } from './clock-in.service';
import { IActivityParticipation } from 'app/shared/model/activity-participation.model';
import { ActivityParticipationService } from 'app/entities/activity-participation';

@Component({
    selector: 'jhi-clock-in-update',
    templateUrl: './clock-in-update.component.html'
})
export class ClockInUpdateComponent implements OnInit {
    private _clockIn: IClockIn;
    isSaving: boolean;

    activityparticipations: IActivityParticipation[];
    punchDateTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private clockInService: ClockInService,
        private activityParticipationService: ActivityParticipationService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ clockIn }) => {
            this.clockIn = clockIn;
        });
        this.activityParticipationService.query().subscribe(
            (res: HttpResponse<IActivityParticipation[]>) => {
                this.activityparticipations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.clockIn.punchDateTime = moment(this.punchDateTime, DATE_TIME_FORMAT);
        if (this.clockIn.id !== undefined) {
            this.subscribeToSaveResponse(this.clockInService.update(this.clockIn));
        } else {
            this.subscribeToSaveResponse(this.clockInService.create(this.clockIn));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IClockIn>>) {
        result.subscribe((res: HttpResponse<IClockIn>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackActivityParticipationById(index: number, item: IActivityParticipation) {
        return item.id;
    }
    get clockIn() {
        return this._clockIn;
    }

    set clockIn(clockIn: IClockIn) {
        this._clockIn = clockIn;
        this.punchDateTime = moment(clockIn.punchDateTime).format(DATE_TIME_FORMAT);
    }
}
