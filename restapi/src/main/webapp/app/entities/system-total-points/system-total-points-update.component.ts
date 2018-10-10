import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISystemTotalPoints } from 'app/shared/model/system-total-points.model';
import { SystemTotalPointsService } from './system-total-points.service';

@Component({
    selector: 'jhi-system-total-points-update',
    templateUrl: './system-total-points-update.component.html'
})
export class SystemTotalPointsUpdateComponent implements OnInit {
    private _systemTotalPoints: ISystemTotalPoints;
    isSaving: boolean;
    createTime: string;
    lastModifyTime: string;

    constructor(private systemTotalPointsService: SystemTotalPointsService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ systemTotalPoints }) => {
            this.systemTotalPoints = systemTotalPoints;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.systemTotalPoints.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        this.systemTotalPoints.lastModifyTime = moment(this.lastModifyTime, DATE_TIME_FORMAT);
        if (this.systemTotalPoints.id !== undefined) {
            this.subscribeToSaveResponse(this.systemTotalPointsService.update(this.systemTotalPoints));
        } else {
            this.subscribeToSaveResponse(this.systemTotalPointsService.create(this.systemTotalPoints));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISystemTotalPoints>>) {
        result.subscribe((res: HttpResponse<ISystemTotalPoints>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get systemTotalPoints() {
        return this._systemTotalPoints;
    }

    set systemTotalPoints(systemTotalPoints: ISystemTotalPoints) {
        this._systemTotalPoints = systemTotalPoints;
        this.createTime = moment(systemTotalPoints.createTime).format(DATE_TIME_FORMAT);
        this.lastModifyTime = moment(systemTotalPoints.lastModifyTime).format(DATE_TIME_FORMAT);
    }
}
