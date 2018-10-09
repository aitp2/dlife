import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserPointDetails } from 'app/shared/model/user-point-details.model';
import { UserPointDetailsService } from './user-point-details.service';

@Component({
    selector: 'jhi-user-point-details-update',
    templateUrl: './user-point-details-update.component.html'
})
export class UserPointDetailsUpdateComponent implements OnInit {
    private _userPointDetails: IUserPointDetails;
    isSaving: boolean;
    applyTime: string;
    createTime: string;
    lastModifyTime: string;

    constructor(private userPointDetailsService: UserPointDetailsService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userPointDetails }) => {
            this.userPointDetails = userPointDetails;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.userPointDetails.applyTime = moment(this.applyTime, DATE_TIME_FORMAT);
        this.userPointDetails.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        this.userPointDetails.lastModifyTime = moment(this.lastModifyTime, DATE_TIME_FORMAT);
        if (this.userPointDetails.id !== undefined) {
            this.subscribeToSaveResponse(this.userPointDetailsService.update(this.userPointDetails));
        } else {
            this.subscribeToSaveResponse(this.userPointDetailsService.create(this.userPointDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUserPointDetails>>) {
        result.subscribe((res: HttpResponse<IUserPointDetails>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get userPointDetails() {
        return this._userPointDetails;
    }

    set userPointDetails(userPointDetails: IUserPointDetails) {
        this._userPointDetails = userPointDetails;
        this.applyTime = moment(userPointDetails.applyTime).format(DATE_TIME_FORMAT);
        this.createTime = moment(userPointDetails.createTime).format(DATE_TIME_FORMAT);
        this.lastModifyTime = moment(userPointDetails.lastModifyTime).format(DATE_TIME_FORMAT);
    }
}
