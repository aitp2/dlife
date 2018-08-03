import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Attendee } from 'app/shared/model/attendee.model';
import { AttendeeService } from './attendee.service';
import { AttendeeComponent } from './attendee.component';
import { AttendeeDetailComponent } from './attendee-detail.component';
import { AttendeeUpdateComponent } from './attendee-update.component';
import { AttendeeDeletePopupComponent } from './attendee-delete-dialog.component';
import { IAttendee } from 'app/shared/model/attendee.model';

@Injectable({ providedIn: 'root' })
export class AttendeeResolve implements Resolve<IAttendee> {
    constructor(private service: AttendeeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((attendee: HttpResponse<Attendee>) => attendee.body));
        }
        return of(new Attendee());
    }
}

export const attendeeRoute: Routes = [
    {
        path: 'attendee',
        component: AttendeeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Attendees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'attendee/:id/view',
        component: AttendeeDetailComponent,
        resolve: {
            attendee: AttendeeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Attendees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'attendee/new',
        component: AttendeeUpdateComponent,
        resolve: {
            attendee: AttendeeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Attendees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'attendee/:id/edit',
        component: AttendeeUpdateComponent,
        resolve: {
            attendee: AttendeeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Attendees'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const attendeePopupRoute: Routes = [
    {
        path: 'attendee/:id/delete',
        component: AttendeeDeletePopupComponent,
        resolve: {
            attendee: AttendeeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Attendees'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
