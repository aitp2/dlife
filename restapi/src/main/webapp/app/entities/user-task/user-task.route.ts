import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserTask } from 'app/shared/model/user-task.model';
import { UserTaskService } from './user-task.service';
import { UserTaskComponent } from './user-task.component';
import { UserTaskDetailComponent } from './user-task-detail.component';
import { UserTaskUpdateComponent } from './user-task-update.component';
import { UserTaskDeletePopupComponent } from './user-task-delete-dialog.component';
import { IUserTask } from 'app/shared/model/user-task.model';

@Injectable({ providedIn: 'root' })
export class UserTaskResolve implements Resolve<IUserTask> {
    constructor(private service: UserTaskService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((userTask: HttpResponse<UserTask>) => userTask.body));
        }
        return of(new UserTask());
    }
}

export const userTaskRoute: Routes = [
    {
        path: 'user-task',
        component: UserTaskComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'UserTasks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-task/:id/view',
        component: UserTaskDetailComponent,
        resolve: {
            userTask: UserTaskResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserTasks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-task/new',
        component: UserTaskUpdateComponent,
        resolve: {
            userTask: UserTaskResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserTasks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-task/:id/edit',
        component: UserTaskUpdateComponent,
        resolve: {
            userTask: UserTaskResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserTasks'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userTaskPopupRoute: Routes = [
    {
        path: 'user-task/:id/delete',
        component: UserTaskDeletePopupComponent,
        resolve: {
            userTask: UserTaskResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserTasks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
