import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ActivityParticipation } from 'app/shared/model/activity-participation.model';
import { ActivityParticipationService } from './activity-participation.service';
import { ActivityParticipationComponent } from './activity-participation.component';
import { ActivityParticipationDetailComponent } from './activity-participation-detail.component';
import { ActivityParticipationUpdateComponent } from './activity-participation-update.component';
import { ActivityParticipationDeletePopupComponent } from './activity-participation-delete-dialog.component';
import { IActivityParticipation } from 'app/shared/model/activity-participation.model';

@Injectable({ providedIn: 'root' })
export class ActivityParticipationResolve implements Resolve<IActivityParticipation> {
    constructor(private service: ActivityParticipationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((activityParticipation: HttpResponse<ActivityParticipation>) => activityParticipation.body));
        }
        return of(new ActivityParticipation());
    }
}

export const activityParticipationRoute: Routes = [
    {
        path: 'activity-participation',
        component: ActivityParticipationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ActivityParticipations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'activity-participation/:id/view',
        component: ActivityParticipationDetailComponent,
        resolve: {
            activityParticipation: ActivityParticipationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActivityParticipations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'activity-participation/new',
        component: ActivityParticipationUpdateComponent,
        resolve: {
            activityParticipation: ActivityParticipationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActivityParticipations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'activity-participation/:id/edit',
        component: ActivityParticipationUpdateComponent,
        resolve: {
            activityParticipation: ActivityParticipationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActivityParticipations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const activityParticipationPopupRoute: Routes = [
    {
        path: 'activity-participation/:id/delete',
        component: ActivityParticipationDeletePopupComponent,
        resolve: {
            activityParticipation: ActivityParticipationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActivityParticipations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
