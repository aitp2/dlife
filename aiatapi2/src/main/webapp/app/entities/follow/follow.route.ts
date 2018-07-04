import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { FollowComponent } from './follow.component';
import { FollowDetailComponent } from './follow-detail.component';
import { FollowPopupComponent } from './follow-dialog.component';
import { FollowDeletePopupComponent } from './follow-delete-dialog.component';

@Injectable()
export class FollowResolvePagingParams implements Resolve<any> {

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

export const followRoute: Routes = [
    {
        path: 'follow',
        component: FollowComponent,
        resolve: {
            'pagingParams': FollowResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Follows'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'follow/:id',
        component: FollowDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Follows'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const followPopupRoute: Routes = [
    {
        path: 'follow-new',
        component: FollowPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Follows'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'follow/:id/edit',
        component: FollowPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Follows'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'follow/:id/delete',
        component: FollowDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Follows'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
