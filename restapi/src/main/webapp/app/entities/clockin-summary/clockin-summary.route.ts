import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ClockinSummary } from 'app/shared/model/clockin-summary.model';
import { ClockinSummaryService } from './clockin-summary.service';
import { ClockinSummaryComponent } from './clockin-summary.component';
import { ClockinSummaryDetailComponent } from './clockin-summary-detail.component';
import { ClockinSummaryUpdateComponent } from './clockin-summary-update.component';
import { ClockinSummaryDeletePopupComponent } from './clockin-summary-delete-dialog.component';
import { IClockinSummary } from 'app/shared/model/clockin-summary.model';

@Injectable({ providedIn: 'root' })
export class ClockinSummaryResolve implements Resolve<IClockinSummary> {
    constructor(private service: ClockinSummaryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((clockinSummary: HttpResponse<ClockinSummary>) => clockinSummary.body));
        }
        return of(new ClockinSummary());
    }
}

export const clockinSummaryRoute: Routes = [
    {
        path: 'clockin-summary',
        component: ClockinSummaryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ClockinSummaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clockin-summary/:id/view',
        component: ClockinSummaryDetailComponent,
        resolve: {
            clockinSummary: ClockinSummaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClockinSummaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clockin-summary/new',
        component: ClockinSummaryUpdateComponent,
        resolve: {
            clockinSummary: ClockinSummaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClockinSummaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clockin-summary/:id/edit',
        component: ClockinSummaryUpdateComponent,
        resolve: {
            clockinSummary: ClockinSummaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClockinSummaries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clockinSummaryPopupRoute: Routes = [
    {
        path: 'clockin-summary/:id/delete',
        component: ClockinSummaryDeletePopupComponent,
        resolve: {
            clockinSummary: ClockinSummaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClockinSummaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
