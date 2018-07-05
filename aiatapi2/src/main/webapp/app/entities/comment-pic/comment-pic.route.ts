import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CommentPicComponent } from './comment-pic.component';
import { CommentPicDetailComponent } from './comment-pic-detail.component';
import { CommentPicPopupComponent } from './comment-pic-dialog.component';
import { CommentPicDeletePopupComponent } from './comment-pic-delete-dialog.component';

@Injectable()
export class CommentPicResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const commentPicRoute: Routes = [
    {
        path: 'comment-pic',
        component: CommentPicComponent,
        resolve: {
            'pagingParams': CommentPicResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommentPics'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'comment-pic/:id',
        component: CommentPicDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommentPics'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commentPicPopupRoute: Routes = [
    {
        path: 'comment-pic-new',
        component: CommentPicPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommentPics'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'comment-pic/:id/edit',
        component: CommentPicPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommentPics'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'comment-pic/:id/delete',
        component: CommentPicDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommentPics'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
