import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { QuestionPic } from 'app/shared/model/question-pic.model';
import { QuestionPicService } from './question-pic.service';
import { QuestionPicComponent } from './question-pic.component';
import { QuestionPicDetailComponent } from './question-pic-detail.component';
import { QuestionPicUpdateComponent } from './question-pic-update.component';
import { QuestionPicDeletePopupComponent } from './question-pic-delete-dialog.component';
import { IQuestionPic } from 'app/shared/model/question-pic.model';

@Injectable({ providedIn: 'root' })
export class QuestionPicResolve implements Resolve<IQuestionPic> {
    constructor(private service: QuestionPicService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((questionPic: HttpResponse<QuestionPic>) => questionPic.body));
        }
        return of(new QuestionPic());
    }
}

export const questionPicRoute: Routes = [
    {
        path: 'question-pic',
        component: QuestionPicComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'QuestionPics'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-pic/:id/view',
        component: QuestionPicDetailComponent,
        resolve: {
            questionPic: QuestionPicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionPics'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-pic/new',
        component: QuestionPicUpdateComponent,
        resolve: {
            questionPic: QuestionPicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionPics'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-pic/:id/edit',
        component: QuestionPicUpdateComponent,
        resolve: {
            questionPic: QuestionPicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionPics'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const questionPicPopupRoute: Routes = [
    {
        path: 'question-pic/:id/delete',
        component: QuestionPicDeletePopupComponent,
        resolve: {
            questionPic: QuestionPicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionPics'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
