import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PinfanPics } from 'app/shared/model/pinfan-pics.model';
import { PinfanPicsService } from './pinfan-pics.service';
import { PinfanPicsComponent } from './pinfan-pics.component';
import { PinfanPicsDetailComponent } from './pinfan-pics-detail.component';
import { PinfanPicsUpdateComponent } from './pinfan-pics-update.component';
import { PinfanPicsDeletePopupComponent } from './pinfan-pics-delete-dialog.component';
import { IPinfanPics } from 'app/shared/model/pinfan-pics.model';

@Injectable({ providedIn: 'root' })
export class PinfanPicsResolve implements Resolve<IPinfanPics> {
    constructor(private service: PinfanPicsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((pinfanPics: HttpResponse<PinfanPics>) => pinfanPics.body));
        }
        return of(new PinfanPics());
    }
}

export const pinfanPicsRoute: Routes = [
    {
        path: 'pinfan-pics',
        component: PinfanPicsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'PinfanPics'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pinfan-pics/:id/view',
        component: PinfanPicsDetailComponent,
        resolve: {
            pinfanPics: PinfanPicsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinfanPics'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pinfan-pics/new',
        component: PinfanPicsUpdateComponent,
        resolve: {
            pinfanPics: PinfanPicsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinfanPics'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pinfan-pics/:id/edit',
        component: PinfanPicsUpdateComponent,
        resolve: {
            pinfanPics: PinfanPicsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinfanPics'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pinfanPicsPopupRoute: Routes = [
    {
        path: 'pinfan-pics/:id/delete',
        component: PinfanPicsDeletePopupComponent,
        resolve: {
            pinfanPics: PinfanPicsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PinfanPics'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
