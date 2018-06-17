import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ClockInComponent } from './clock-in.component';
import { ClockInDetailComponent } from './clock-in-detail.component';
import { ClockInPopupComponent } from './clock-in-dialog.component';
import { ClockInDeletePopupComponent } from './clock-in-delete-dialog.component';

@Injectable()
export class ClockInResolvePagingParams implements Resolve<any> {

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

export const clockInRoute: Routes = [
    {
        path: 'clock-in',
        component: ClockInComponent,
        resolve: {
            'pagingParams': ClockInResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.clockIn.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'clock-in/:id',
        component: ClockInDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.clockIn.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clockInPopupRoute: Routes = [
    {
        path: 'clock-in-new',
        component: ClockInPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.clockIn.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clock-in/:id/edit',
        component: ClockInPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.clockIn.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clock-in/:id/delete',
        component: ClockInDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.clockIn.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
