import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ThumbsUp } from 'app/shared/model/thumbs-up.model';
import { ThumbsUpService } from './thumbs-up.service';
import { ThumbsUpComponent } from './thumbs-up.component';
import { ThumbsUpDetailComponent } from './thumbs-up-detail.component';
import { ThumbsUpUpdateComponent } from './thumbs-up-update.component';
import { ThumbsUpDeletePopupComponent } from './thumbs-up-delete-dialog.component';
import { IThumbsUp } from 'app/shared/model/thumbs-up.model';

@Injectable({ providedIn: 'root' })
export class ThumbsUpResolve implements Resolve<IThumbsUp> {
    constructor(private service: ThumbsUpService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((thumbsUp: HttpResponse<ThumbsUp>) => thumbsUp.body));
        }
        return of(new ThumbsUp());
    }
}

export const thumbsUpRoute: Routes = [
    {
        path: 'thumbs-up',
        component: ThumbsUpComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ThumbsUps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'thumbs-up/:id/view',
        component: ThumbsUpDetailComponent,
        resolve: {
            thumbsUp: ThumbsUpResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ThumbsUps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'thumbs-up/new',
        component: ThumbsUpUpdateComponent,
        resolve: {
            thumbsUp: ThumbsUpResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ThumbsUps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'thumbs-up/:id/edit',
        component: ThumbsUpUpdateComponent,
        resolve: {
            thumbsUp: ThumbsUpResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ThumbsUps'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const thumbsUpPopupRoute: Routes = [
    {
        path: 'thumbs-up/:id/delete',
        component: ThumbsUpDeletePopupComponent,
        resolve: {
            thumbsUp: ThumbsUpResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ThumbsUps'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
