import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ActivityParticipationComponent } from './activity-participation.component';
import { ActivityParticipationDetailComponent } from './activity-participation-detail.component';
import { ActivityParticipationPopupComponent } from './activity-participation-dialog.component';
import { ActivityParticipationDeletePopupComponent } from './activity-participation-delete-dialog.component';

@Injectable()
export class ActivityParticipationResolvePagingParams implements Resolve<any> {

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

export const activityParticipationRoute: Routes = [
    {
        path: 'activity-participation',
        component: ActivityParticipationComponent,
        resolve: {
            'pagingParams': ActivityParticipationResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActivityParticipations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'activity-participation/:id',
        component: ActivityParticipationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActivityParticipations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const activityParticipationPopupRoute: Routes = [
    {
        path: 'activity-participation-new',
        component: ActivityParticipationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActivityParticipations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'activity-participation/:id/edit',
        component: ActivityParticipationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActivityParticipations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'activity-participation/:id/delete',
        component: ActivityParticipationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActivityParticipations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
