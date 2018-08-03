import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { WechatUser } from 'app/shared/model/wechat-user.model';
import { WechatUserService } from './wechat-user.service';
import { WechatUserComponent } from './wechat-user.component';
import { WechatUserDetailComponent } from './wechat-user-detail.component';
import { WechatUserUpdateComponent } from './wechat-user-update.component';
import { WechatUserDeletePopupComponent } from './wechat-user-delete-dialog.component';
import { IWechatUser } from 'app/shared/model/wechat-user.model';

@Injectable({ providedIn: 'root' })
export class WechatUserResolve implements Resolve<IWechatUser> {
    constructor(private service: WechatUserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((wechatUser: HttpResponse<WechatUser>) => wechatUser.body));
        }
        return of(new WechatUser());
    }
}

export const wechatUserRoute: Routes = [
    {
        path: 'wechat-user',
        component: WechatUserComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'WechatUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wechat-user/:id/view',
        component: WechatUserDetailComponent,
        resolve: {
            wechatUser: WechatUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wechat-user/new',
        component: WechatUserUpdateComponent,
        resolve: {
            wechatUser: WechatUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wechat-user/:id/edit',
        component: WechatUserUpdateComponent,
        resolve: {
            wechatUser: WechatUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatUsers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const wechatUserPopupRoute: Routes = [
    {
        path: 'wechat-user/:id/delete',
        component: WechatUserDeletePopupComponent,
        resolve: {
            wechatUser: WechatUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
