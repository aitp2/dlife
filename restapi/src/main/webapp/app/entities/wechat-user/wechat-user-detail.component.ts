import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWechatUser } from 'app/shared/model/wechat-user.model';

@Component({
    selector: 'jhi-wechat-user-detail',
    templateUrl: './wechat-user-detail.component.html'
})
export class WechatUserDetailComponent implements OnInit {
    wechatUser: IWechatUser;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ wechatUser }) => {
            this.wechatUser = wechatUser;
        });
    }

    previousState() {
        window.history.back();
    }
}
