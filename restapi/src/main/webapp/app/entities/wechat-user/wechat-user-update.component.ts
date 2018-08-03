import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IWechatUser } from 'app/shared/model/wechat-user.model';
import { WechatUserService } from './wechat-user.service';

@Component({
    selector: 'jhi-wechat-user-update',
    templateUrl: './wechat-user-update.component.html'
})
export class WechatUserUpdateComponent implements OnInit {
    private _wechatUser: IWechatUser;
    isSaving: boolean;
    createTime: string;
    modifyTime: string;

    constructor(private wechatUserService: WechatUserService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ wechatUser }) => {
            this.wechatUser = wechatUser;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.wechatUser.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        this.wechatUser.modifyTime = moment(this.modifyTime, DATE_TIME_FORMAT);
        if (this.wechatUser.id !== undefined) {
            this.subscribeToSaveResponse(this.wechatUserService.update(this.wechatUser));
        } else {
            this.subscribeToSaveResponse(this.wechatUserService.create(this.wechatUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IWechatUser>>) {
        result.subscribe((res: HttpResponse<IWechatUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get wechatUser() {
        return this._wechatUser;
    }

    set wechatUser(wechatUser: IWechatUser) {
        this._wechatUser = wechatUser;
        this.createTime = moment(wechatUser.createTime).format(DATE_TIME_FORMAT);
        this.modifyTime = moment(wechatUser.modifyTime).format(DATE_TIME_FORMAT);
    }
}
