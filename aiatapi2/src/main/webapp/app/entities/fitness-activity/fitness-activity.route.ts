import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { FitnessActivity } from 'app/shared/model/fitness-activity.model';
import { FitnessActivityService } from './fitness-activity.service';
import { FitnessActivityComponent } from './fitness-activity.component';
import { FitnessActivityDetailComponent } from './fitness-activity-detail.component';
import { FitnessActivityUpdateComponent } from './fitness-activity-update.component';
import { FitnessActivityDeletePopupComponent } from './fitness-activity-delete-dialog.component';
import { IFitnessActivity } from 'app/shared/model/fitness-activity.model';

@Injectable({ providedIn: 'root' })
export class FitnessActivityResolve implements Resolve<IFitnessActivity> {
    constructor(private service: FitnessActivityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((fitnessActivity: HttpResponse<FitnessActivity>) => fitnessActivity.body));
        }
        return of(new FitnessActivity());
    }
}

export const fitnessActivityRoute: Routes = [
    {
        path: 'fitness-activity',
        component: FitnessActivityComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'FitnessActivities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'fitness-activity/:id/view',
        component: FitnessActivityDetailComponent,
        resolve: {
            fitnessActivity: FitnessActivityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FitnessActivities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'fitness-activity/new',
        component: FitnessActivityUpdateComponent,
        resolve: {
            fitnessActivity: FitnessActivityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FitnessActivities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'fitness-activity/:id/edit',
        component: FitnessActivityUpdateComponent,
        resolve: {
            fitnessActivity: FitnessActivityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FitnessActivities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fitnessActivityPopupRoute: Routes = [
    {
        path: 'fitness-activity/:id/delete',
        component: FitnessActivityDeletePopupComponent,
        resolve: {
            fitnessActivity: FitnessActivityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FitnessActivities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
