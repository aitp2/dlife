import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TaskDefine } from 'app/shared/model/task-define.model';
import { TaskDefineService } from './task-define.service';
import { TaskDefineComponent } from './task-define.component';
import { TaskDefineDetailComponent } from './task-define-detail.component';
import { TaskDefineUpdateComponent } from './task-define-update.component';
import { TaskDefineDeletePopupComponent } from './task-define-delete-dialog.component';
import { ITaskDefine } from 'app/shared/model/task-define.model';

@Injectable({ providedIn: 'root' })
export class TaskDefineResolve implements Resolve<ITaskDefine> {
    constructor(private service: TaskDefineService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((taskDefine: HttpResponse<TaskDefine>) => taskDefine.body));
        }
        return of(new TaskDefine());
    }
}

export const taskDefineRoute: Routes = [
    {
        path: 'task-define',
        component: TaskDefineComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'TaskDefines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'task-define/:id/view',
        component: TaskDefineDetailComponent,
        resolve: {
            taskDefine: TaskDefineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskDefines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'task-define/new',
        component: TaskDefineUpdateComponent,
        resolve: {
            taskDefine: TaskDefineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskDefines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'task-define/:id/edit',
        component: TaskDefineUpdateComponent,
        resolve: {
            taskDefine: TaskDefineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskDefines'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const taskDefinePopupRoute: Routes = [
    {
        path: 'task-define/:id/delete',
        component: TaskDefineDeletePopupComponent,
        resolve: {
            taskDefine: TaskDefineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskDefines'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
