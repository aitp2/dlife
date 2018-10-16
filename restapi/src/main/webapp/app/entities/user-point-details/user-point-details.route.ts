import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserPointDetails } from 'app/shared/model/user-point-details.model';
import { UserPointDetailsService } from './user-point-details.service';
import { UserPointDetailsComponent } from './user-point-details.component';
import { UserPointDetailsDetailComponent } from './user-point-details-detail.component';
import { UserPointDetailsUpdateComponent } from './user-point-details-update.component';
import { UserPointDetailsDeletePopupComponent } from './user-point-details-delete-dialog.component';
import { IUserPointDetails } from 'app/shared/model/user-point-details.model';

@Injectable({ providedIn: 'root' })
export class UserPointDetailsResolve implements Resolve<IUserPointDetails> {
    constructor(private service: UserPointDetailsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((userPointDetails: HttpResponse<UserPointDetails>) => userPointDetails.body));
        }
        return of(new UserPointDetails());
    }
}

export const userPointDetailsRoute: Routes = [
    {
        path: 'user-point-details',
        component: UserPointDetailsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'UserPointDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-point-details/:id/view',
        component: UserPointDetailsDetailComponent,
        resolve: {
            userPointDetails: UserPointDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPointDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-point-details/new',
        component: UserPointDetailsUpdateComponent,
        resolve: {
            userPointDetails: UserPointDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPointDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-point-details/:id/edit',
        component: UserPointDetailsUpdateComponent,
        resolve: {
            userPointDetails: UserPointDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPointDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userPointDetailsPopupRoute: Routes = [
    {
        path: 'user-point-details/:id/delete',
        component: UserPointDetailsDeletePopupComponent,
        resolve: {
            userPointDetails: UserPointDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPointDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
