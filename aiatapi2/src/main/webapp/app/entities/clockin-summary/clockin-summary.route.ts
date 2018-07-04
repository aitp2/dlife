import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ClockinSummaryComponent } from './clockin-summary.component';
import { ClockinSummaryDetailComponent } from './clockin-summary-detail.component';
import { ClockinSummaryPopupComponent } from './clockin-summary-dialog.component';
import { ClockinSummaryDeletePopupComponent } from './clockin-summary-delete-dialog.component';

@Injectable()
export class ClockinSummaryResolvePagingParams implements Resolve<any> {

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

export const clockinSummaryRoute: Routes = [
    {
        path: 'clockin-summary',
        component: ClockinSummaryComponent,
        resolve: {
            'pagingParams': ClockinSummaryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClockinSummaries'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'clockin-summary/:id',
        component: ClockinSummaryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClockinSummaries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clockinSummaryPopupRoute: Routes = [
    {
        path: 'clockin-summary-new',
        component: ClockinSummaryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClockinSummaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clockin-summary/:id/edit',
        component: ClockinSummaryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClockinSummaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clockin-summary/:id/delete',
        component: ClockinSummaryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClockinSummaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
