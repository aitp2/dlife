import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PinfanPicsComponent } from './pinfan-pics.component';
import { PinfanPicsDetailComponent } from './pinfan-pics-detail.component';
import { PinfanPicsPopupComponent } from './pinfan-pics-dialog.component';
import { PinfanPicsDeletePopupComponent } from './pinfan-pics-delete-dialog.component';

@Injectable()
export class PinfanPicsResolvePagingParams implements Resolve<any> {

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

export const pinfanPicsRoute: Routes = [
    {
        path: 'pinfan-pics',
        component: PinfanPicsComponent,
        resolve: {
            'pagingParams': PinfanPicsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinfanPics'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pinfan-pics/:id',
        component: PinfanPicsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinfanPics'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pinfanPicsPopupRoute: Routes = [
    {
        path: 'pinfan-pics-new',
        component: PinfanPicsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinfanPics'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pinfan-pics/:id/edit',
        component: PinfanPicsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinfanPics'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pinfan-pics/:id/delete',
        component: PinfanPicsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinfanPics'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
