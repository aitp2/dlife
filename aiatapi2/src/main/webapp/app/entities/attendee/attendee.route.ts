import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AttendeeComponent } from './attendee.component';
import { AttendeeDetailComponent } from './attendee-detail.component';
import { AttendeePopupComponent } from './attendee-dialog.component';
import { AttendeeDeletePopupComponent } from './attendee-delete-dialog.component';

@Injectable()
export class AttendeeResolvePagingParams implements Resolve<any> {

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

export const attendeeRoute: Routes = [
    {
        path: 'attendee',
        component: AttendeeComponent,
        resolve: {
            'pagingParams': AttendeeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Attendees'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'attendee/:id',
        component: AttendeeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Attendees'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const attendeePopupRoute: Routes = [
    {
        path: 'attendee-new',
        component: AttendeePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Attendees'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'attendee/:id/edit',
        component: AttendeePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Attendees'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'attendee/:id/delete',
        component: AttendeeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Attendees'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
