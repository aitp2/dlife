import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PinFanActivityComponent } from './pin-fan-activity.component';
import { PinFanActivityDetailComponent } from './pin-fan-activity-detail.component';
import { PinFanActivityPopupComponent } from './pin-fan-activity-dialog.component';
import { PinFanActivityDeletePopupComponent } from './pin-fan-activity-delete-dialog.component';

@Injectable()
export class PinFanActivityResolvePagingParams implements Resolve<any> {

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

export const pinFanActivityRoute: Routes = [
    {
        path: 'pin-fan-activity',
        component: PinFanActivityComponent,
        resolve: {
            'pagingParams': PinFanActivityResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinFanActivities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pin-fan-activity/:id',
        component: PinFanActivityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinFanActivities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pinFanActivityPopupRoute: Routes = [
    {
        path: 'pin-fan-activity-new',
        component: PinFanActivityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinFanActivities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pin-fan-activity/:id/edit',
        component: PinFanActivityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinFanActivities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pin-fan-activity/:id/delete',
        component: PinFanActivityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinFanActivities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
