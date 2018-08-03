import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CommentPic } from 'app/shared/model/comment-pic.model';
import { CommentPicService } from './comment-pic.service';
import { CommentPicComponent } from './comment-pic.component';
import { CommentPicDetailComponent } from './comment-pic-detail.component';
import { CommentPicUpdateComponent } from './comment-pic-update.component';
import { CommentPicDeletePopupComponent } from './comment-pic-delete-dialog.component';
import { ICommentPic } from 'app/shared/model/comment-pic.model';

@Injectable({ providedIn: 'root' })
export class CommentPicResolve implements Resolve<ICommentPic> {
    constructor(private service: CommentPicService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((commentPic: HttpResponse<CommentPic>) => commentPic.body));
        }
        return of(new CommentPic());
    }
}

export const commentPicRoute: Routes = [
    {
        path: 'comment-pic',
        component: CommentPicComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'CommentPics'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'comment-pic/:id/view',
        component: CommentPicDetailComponent,
        resolve: {
            commentPic: CommentPicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommentPics'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'comment-pic/new',
        component: CommentPicUpdateComponent,
        resolve: {
            commentPic: CommentPicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommentPics'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'comment-pic/:id/edit',
        component: CommentPicUpdateComponent,
        resolve: {
            commentPic: CommentPicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommentPics'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commentPicPopupRoute: Routes = [
    {
        path: 'comment-pic/:id/delete',
        component: CommentPicDeletePopupComponent,
        resolve: {
            commentPic: CommentPicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommentPics'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
