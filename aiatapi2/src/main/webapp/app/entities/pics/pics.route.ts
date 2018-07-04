import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PicsComponent } from './pics.component';
import { PicsDetailComponent } from './pics-detail.component';
import { PicsPopupComponent } from './pics-dialog.component';
import { PicsDeletePopupComponent } from './pics-delete-dialog.component';

@Injectable()
export class PicsResolvePagingParams implements Resolve<any> {

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

export const picsRoute: Routes = [
    {
        path: 'pics',
        component: PicsComponent,
        resolve: {
            'pagingParams': PicsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pics'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pics/:id',
        component: PicsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pics'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const picsPopupRoute: Routes = [
    {
        path: 'pics-new',
        component: PicsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pics'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pics/:id/edit',
        component: PicsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pics'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pics/:id/delete',
        component: PicsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pics'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
