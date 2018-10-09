import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SystemTotalPoints } from 'app/shared/model/system-total-points.model';
import { SystemTotalPointsService } from './system-total-points.service';
import { SystemTotalPointsComponent } from './system-total-points.component';
import { SystemTotalPointsDetailComponent } from './system-total-points-detail.component';
import { SystemTotalPointsUpdateComponent } from './system-total-points-update.component';
import { SystemTotalPointsDeletePopupComponent } from './system-total-points-delete-dialog.component';
import { ISystemTotalPoints } from 'app/shared/model/system-total-points.model';

@Injectable({ providedIn: 'root' })
export class SystemTotalPointsResolve implements Resolve<ISystemTotalPoints> {
    constructor(private service: SystemTotalPointsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((systemTotalPoints: HttpResponse<SystemTotalPoints>) => systemTotalPoints.body));
        }
        return of(new SystemTotalPoints());
    }
}

export const systemTotalPointsRoute: Routes = [
    {
        path: 'system-total-points',
        component: SystemTotalPointsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'SystemTotalPoints'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'system-total-points/:id/view',
        component: SystemTotalPointsDetailComponent,
        resolve: {
            systemTotalPoints: SystemTotalPointsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemTotalPoints'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'system-total-points/new',
        component: SystemTotalPointsUpdateComponent,
        resolve: {
            systemTotalPoints: SystemTotalPointsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemTotalPoints'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'system-total-points/:id/edit',
        component: SystemTotalPointsUpdateComponent,
        resolve: {
            systemTotalPoints: SystemTotalPointsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemTotalPoints'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const systemTotalPointsPopupRoute: Routes = [
    {
        path: 'system-total-points/:id/delete',
        component: SystemTotalPointsDeletePopupComponent,
        resolve: {
            systemTotalPoints: SystemTotalPointsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemTotalPoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
