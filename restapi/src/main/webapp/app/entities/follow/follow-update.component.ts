import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFollow } from 'app/shared/model/follow.model';
import { FollowService } from './follow.service';

@Component({
    selector: 'jhi-follow-update',
    templateUrl: './follow-update.component.html'
})
export class FollowUpdateComponent implements OnInit {
    private _follow: IFollow;
    isSaving: boolean;
    createTime: string;
    modifyTime: string;

    constructor(private followService: FollowService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ follow }) => {
            this.follow = follow;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.follow.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        this.follow.modifyTime = moment(this.modifyTime, DATE_TIME_FORMAT);
        if (this.follow.id !== undefined) {
            this.subscribeToSaveResponse(this.followService.update(this.follow));
        } else {
            this.subscribeToSaveResponse(this.followService.create(this.follow));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFollow>>) {
        result.subscribe((res: HttpResponse<IFollow>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get follow() {
        return this._follow;
    }

    set follow(follow: IFollow) {
        this._follow = follow;
        this.createTime = moment(follow.createTime).format(DATE_TIME_FORMAT);
        this.modifyTime = moment(follow.modifyTime).format(DATE_TIME_FORMAT);
    }
}
