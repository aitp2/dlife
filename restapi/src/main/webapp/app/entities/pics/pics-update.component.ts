import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPics } from 'app/shared/model/pics.model';
import { PicsService } from './pics.service';
import { IFitnessActivity } from 'app/shared/model/fitness-activity.model';
import { FitnessActivityService } from 'app/entities/fitness-activity';
import { IClockIn } from 'app/shared/model/clock-in.model';
import { ClockInService } from 'app/entities/clock-in';

@Component({
    selector: 'jhi-pics-update',
    templateUrl: './pics-update.component.html'
})
export class PicsUpdateComponent implements OnInit {
    private _pics: IPics;
    isSaving: boolean;

    fitnessactivities: IFitnessActivity[];

    clockins: IClockIn[];
    createTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private picsService: PicsService,
        private fitnessActivityService: FitnessActivityService,
        private clockInService: ClockInService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pics }) => {
            this.pics = pics;
        });
        this.fitnessActivityService.query().subscribe(
            (res: HttpResponse<IFitnessActivity[]>) => {
                this.fitnessactivities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.clockInService.query().subscribe(
            (res: HttpResponse<IClockIn[]>) => {
                this.clockins = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.pics.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        if (this.pics.id !== undefined) {
            this.subscribeToSaveResponse(this.picsService.update(this.pics));
        } else {
            this.subscribeToSaveResponse(this.picsService.create(this.pics));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPics>>) {
        result.subscribe((res: HttpResponse<IPics>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackClockInById(index: number, item: IClockIn) {
        return item.id;
    }
    get pics() {
        return this._pics;
    }

    set pics(pics: IPics) {
        this._pics = pics;
        this.createTime = moment(pics.createTime).format(DATE_TIME_FORMAT);
    }
}
