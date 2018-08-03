import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PinFanActivity } from 'app/shared/model/pin-fan-activity.model';
import { PinFanActivityService } from './pin-fan-activity.service';
import { PinFanActivityComponent } from './pin-fan-activity.component';
import { PinFanActivityDetailComponent } from './pin-fan-activity-detail.component';
import { PinFanActivityUpdateComponent } from './pin-fan-activity-update.component';
import { PinFanActivityDeletePopupComponent } from './pin-fan-activity-delete-dialog.component';
import { IPinFanActivity } from 'app/shared/model/pin-fan-activity.model';

@Injectable({ providedIn: 'root' })
export class PinFanActivityResolve implements Resolve<IPinFanActivity> {
    constructor(private service: PinFanActivityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((pinFanActivity: HttpResponse<PinFanActivity>) => pinFanActivity.body));
        }
        return of(new PinFanActivity());
    }
}

export const pinFanActivityRoute: Routes = [
    {
        path: 'pin-fan-activity',
        component: PinFanActivityComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'PinFanActivities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pin-fan-activity/:id/view',
        component: PinFanActivityDetailComponent,
        resolve: {
            pinFanActivity: PinFanActivityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinFanActivities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pin-fan-activity/new',
        component: PinFanActivityUpdateComponent,
        resolve: {
            pinFanActivity: PinFanActivityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinFanActivities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pin-fan-activity/:id/edit',
        component: PinFanActivityUpdateComponent,
        resolve: {
            pinFanActivity: PinFanActivityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinFanActivities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pinFanActivityPopupRoute: Routes = [
    {
        path: 'pin-fan-activity/:id/delete',
        component: PinFanActivityDeletePopupComponent,
        resolve: {
            pinFanActivity: PinFanActivityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinFanActivities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
