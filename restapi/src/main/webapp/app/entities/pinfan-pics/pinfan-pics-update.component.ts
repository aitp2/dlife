import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPinfanPics } from 'app/shared/model/pinfan-pics.model';
import { PinfanPicsService } from './pinfan-pics.service';
import { IPinFanActivity } from 'app/shared/model/pin-fan-activity.model';
import { PinFanActivityService } from 'app/entities/pin-fan-activity';

@Component({
    selector: 'jhi-pinfan-pics-update',
    templateUrl: './pinfan-pics-update.component.html'
})
export class PinfanPicsUpdateComponent implements OnInit {
    private _pinfanPics: IPinfanPics;
    isSaving: boolean;

    pinfanactivities: IPinFanActivity[];
    createTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private pinfanPicsService: PinfanPicsService,
        private pinFanActivityService: PinFanActivityService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pinfanPics }) => {
            this.pinfanPics = pinfanPics;
        });
        this.pinFanActivityService.query().subscribe(
            (res: HttpResponse<IPinFanActivity[]>) => {
                this.pinfanactivities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.pinfanPics.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        if (this.pinfanPics.id !== undefined) {
            this.subscribeToSaveResponse(this.pinfanPicsService.update(this.pinfanPics));
        } else {
            this.subscribeToSaveResponse(this.pinfanPicsService.create(this.pinfanPics));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPinfanPics>>) {
        result.subscribe((res: HttpResponse<IPinfanPics>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPinFanActivityById(index: number, item: IPinFanActivity) {
        return item.id;
    }
    get pinfanPics() {
        return this._pinfanPics;
    }

    set pinfanPics(pinfanPics: IPinfanPics) {
        this._pinfanPics = pinfanPics;
        this.createTime = moment(pinfanPics.createTime).format(DATE_TIME_FORMAT);
    }
}
