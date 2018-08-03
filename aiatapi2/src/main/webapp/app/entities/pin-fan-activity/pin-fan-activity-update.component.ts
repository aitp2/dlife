import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPinFanActivity } from 'app/shared/model/pin-fan-activity.model';
import { PinFanActivityService } from './pin-fan-activity.service';

@Component({
    selector: 'jhi-pin-fan-activity-update',
    templateUrl: './pin-fan-activity-update.component.html'
})
export class PinFanActivityUpdateComponent implements OnInit {
    private _pinFanActivity: IPinFanActivity;
    isSaving: boolean;
    appointDatetime: string;
    appointEndDatetime: string;
    deadline: string;
    modifyTime: string;

    constructor(private pinFanActivityService: PinFanActivityService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pinFanActivity }) => {
            this.pinFanActivity = pinFanActivity;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.pinFanActivity.appointDatetime = moment(this.appointDatetime, DATE_TIME_FORMAT);
        this.pinFanActivity.appointEndDatetime = moment(this.appointEndDatetime, DATE_TIME_FORMAT);
        this.pinFanActivity.deadline = moment(this.deadline, DATE_TIME_FORMAT);
        this.pinFanActivity.modifyTime = moment(this.modifyTime, DATE_TIME_FORMAT);
        if (this.pinFanActivity.id !== undefined) {
            this.subscribeToSaveResponse(this.pinFanActivityService.update(this.pinFanActivity));
        } else {
            this.subscribeToSaveResponse(this.pinFanActivityService.create(this.pinFanActivity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPinFanActivity>>) {
        result.subscribe((res: HttpResponse<IPinFanActivity>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get pinFanActivity() {
        return this._pinFanActivity;
    }

    set pinFanActivity(pinFanActivity: IPinFanActivity) {
        this._pinFanActivity = pinFanActivity;
        this.appointDatetime = moment(pinFanActivity.appointDatetime).format(DATE_TIME_FORMAT);
        this.appointEndDatetime = moment(pinFanActivity.appointEndDatetime).format(DATE_TIME_FORMAT);
        this.deadline = moment(pinFanActivity.deadline).format(DATE_TIME_FORMAT);
        this.modifyTime = moment(pinFanActivity.modifyTime).format(DATE_TIME_FORMAT);
    }
}
