import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { EvaluateComponent } from './evaluate.component';
import { EvaluateDetailComponent } from './evaluate-detail.component';
import { EvaluatePopupComponent } from './evaluate-dialog.component';
import { EvaluateDeletePopupComponent } from './evaluate-delete-dialog.component';

@Injectable()
export class EvaluateResolvePagingParams implements Resolve<any> {

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

export const evaluateRoute: Routes = [
    {
        path: 'evaluate',
        component: EvaluateComponent,
        resolve: {
            'pagingParams': EvaluateResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.evaluate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'evaluate/:id',
        component: EvaluateDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.evaluate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const evaluatePopupRoute: Routes = [
    {
        path: 'evaluate-new',
        component: EvaluatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.evaluate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'evaluate/:id/edit',
        component: EvaluatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.evaluate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'evaluate/:id/delete',
        component: EvaluateDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.evaluate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
