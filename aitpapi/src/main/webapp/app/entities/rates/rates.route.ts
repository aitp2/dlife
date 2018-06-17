import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { RatesComponent } from './rates.component';
import { RatesDetailComponent } from './rates-detail.component';
import { RatesPopupComponent } from './rates-dialog.component';
import { RatesDeletePopupComponent } from './rates-delete-dialog.component';

@Injectable()
export class RatesResolvePagingParams implements Resolve<any> {

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

export const ratesRoute: Routes = [
    {
        path: 'rates',
        component: RatesComponent,
        resolve: {
            'pagingParams': RatesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.rates.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'rates/:id',
        component: RatesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.rates.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ratesPopupRoute: Routes = [
    {
        path: 'rates-new',
        component: RatesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.rates.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rates/:id/edit',
        component: RatesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.rates.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rates/:id/delete',
        component: RatesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aitpapiApp.rates.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
