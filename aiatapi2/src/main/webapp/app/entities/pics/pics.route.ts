import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Pics } from 'app/shared/model/pics.model';
import { PicsService } from './pics.service';
import { PicsComponent } from './pics.component';
import { PicsDetailComponent } from './pics-detail.component';
import { PicsUpdateComponent } from './pics-update.component';
import { PicsDeletePopupComponent } from './pics-delete-dialog.component';
import { IPics } from 'app/shared/model/pics.model';

@Injectable({ providedIn: 'root' })
export class PicsResolve implements Resolve<IPics> {
    constructor(private service: PicsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((pics: HttpResponse<Pics>) => pics.body));
        }
        return of(new Pics());
    }
}

export const picsRoute: Routes = [
    {
        path: 'pics',
        component: PicsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Pics'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pics/:id/view',
        component: PicsDetailComponent,
        resolve: {
            pics: PicsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pics'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pics/new',
        component: PicsUpdateComponent,
        resolve: {
            pics: PicsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pics'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pics/:id/edit',
        component: PicsUpdateComponent,
        resolve: {
            pics: PicsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pics'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const picsPopupRoute: Routes = [
    {
        path: 'pics/:id/delete',
        component: PicsDeletePopupComponent,
        resolve: {
            pics: PicsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pics'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
