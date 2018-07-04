import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { WechatUserComponent } from './wechat-user.component';
import { WechatUserDetailComponent } from './wechat-user-detail.component';
import { WechatUserPopupComponent } from './wechat-user-dialog.component';
import { WechatUserDeletePopupComponent } from './wechat-user-delete-dialog.component';

@Injectable()
export class WechatUserResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const wechatUserRoute: Routes = [
    {
        path: 'wechat-user',
        component: WechatUserComponent,
        resolve: {
            'pagingParams': WechatUserResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatUsers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'wechat-user/:id',
        component: WechatUserDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatUsers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const wechatUserPopupRoute: Routes = [
    {
        path: 'wechat-user-new',
        component: WechatUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wechat-user/:id/edit',
        component: WechatUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wechat-user/:id/delete',
        component: WechatUserDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WechatUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
