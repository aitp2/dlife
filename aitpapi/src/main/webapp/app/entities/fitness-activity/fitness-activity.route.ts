import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { FitnessActivityComponent } from './fitness-activity.component';
import { FitnessActivityDetailComponent } from './fitness-activity-detail.component';
import { FitnessActivityPopupComponent } from './fitness-activity-dialog.component';
import { FitnessActivityDeletePopupComponent } from './fitness-activity-delete-dialog.component';

@Injectable()
export class FitnessActivityResolvePagingParams implements Resolve<any> {

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

export const fitnessActivityRoute: Routes = [
    {
        path: 'fitness-activity',
        component: FitnessActivityComponent,
        resolve: {
            'pagingParams': FitnessActivityResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.fitnessActivity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'fitness-activity/:id',
        component: FitnessActivityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.fitnessActivity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fitnessActivityPopupRoute: Routes = [
    {
        path: 'fitness-activity-new',
        component: FitnessActivityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.fitnessActivity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fitness-activity/:id/edit',
        component: FitnessActivityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.fitnessActivity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fitness-activity/:id/delete',
        component: FitnessActivityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.fitnessActivity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
