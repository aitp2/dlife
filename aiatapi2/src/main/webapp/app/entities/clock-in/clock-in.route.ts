import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ClockIn } from 'app/shared/model/clock-in.model';
import { ClockInService } from './clock-in.service';
import { ClockInComponent } from './clock-in.component';
import { ClockInDetailComponent } from './clock-in-detail.component';
import { ClockInUpdateComponent } from './clock-in-update.component';
import { ClockInDeletePopupComponent } from './clock-in-delete-dialog.component';
import { IClockIn } from 'app/shared/model/clock-in.model';

@Injectable({ providedIn: 'root' })
export class ClockInResolve implements Resolve<IClockIn> {
    constructor(private service: ClockInService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((clockIn: HttpResponse<ClockIn>) => clockIn.body));
        }
        return of(new ClockIn());
    }
}

export const clockInRoute: Routes = [
    {
        path: 'clock-in',
        component: ClockInComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ClockIns'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clock-in/:id/view',
        component: ClockInDetailComponent,
        resolve: {
            clockIn: ClockInResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClockIns'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clock-in/new',
        component: ClockInUpdateComponent,
        resolve: {
            clockIn: ClockInResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClockIns'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clock-in/:id/edit',
        component: ClockInUpdateComponent,
        resolve: {
            clockIn: ClockInResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClockIns'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clockInPopupRoute: Routes = [
    {
        path: 'clock-in/:id/delete',
        component: ClockInDeletePopupComponent,
        resolve: {
            clockIn: ClockInResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClockIns'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
