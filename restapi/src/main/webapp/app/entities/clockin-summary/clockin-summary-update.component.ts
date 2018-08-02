import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IClockinSummary } from 'app/shared/model/clockin-summary.model';
import { ClockinSummaryService } from './clockin-summary.service';

@Component({
    selector: 'jhi-clockin-summary-update',
    templateUrl: './clockin-summary-update.component.html'
})
export class ClockinSummaryUpdateComponent implements OnInit {
    private _clockinSummary: IClockinSummary;
    isSaving: boolean;
    lastClockInTime: string;

    constructor(private clockinSummaryService: ClockinSummaryService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ clockinSummary }) => {
            this.clockinSummary = clockinSummary;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.clockinSummary.lastClockInTime = moment(this.lastClockInTime, DATE_TIME_FORMAT);
        if (this.clockinSummary.id !== undefined) {
            this.subscribeToSaveResponse(this.clockinSummaryService.update(this.clockinSummary));
        } else {
            this.subscribeToSaveResponse(this.clockinSummaryService.create(this.clockinSummary));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IClockinSummary>>) {
        result.subscribe((res: HttpResponse<IClockinSummary>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get clockinSummary() {
        return this._clockinSummary;
    }

    set clockinSummary(clockinSummary: IClockinSummary) {
        this._clockinSummary = clockinSummary;
        this.lastClockInTime = moment(clockinSummary.lastClockInTime).format(DATE_TIME_FORMAT);
    }
}
