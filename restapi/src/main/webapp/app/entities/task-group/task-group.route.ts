import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TaskGroup } from 'app/shared/model/task-group.model';
import { TaskGroupService } from './task-group.service';
import { TaskGroupComponent } from './task-group.component';
import { TaskGroupDetailComponent } from './task-group-detail.component';
import { TaskGroupUpdateComponent } from './task-group-update.component';
import { TaskGroupDeletePopupComponent } from './task-group-delete-dialog.component';
import { ITaskGroup } from 'app/shared/model/task-group.model';

@Injectable({ providedIn: 'root' })
export class TaskGroupResolve implements Resolve<ITaskGroup> {
    constructor(private service: TaskGroupService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((taskGroup: HttpResponse<TaskGroup>) => taskGroup.body));
        }
        return of(new TaskGroup());
    }
}

export const taskGroupRoute: Routes = [
    {
        path: 'task-group',
        component: TaskGroupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'task-group/:id/view',
        component: TaskGroupDetailComponent,
        resolve: {
            taskGroup: TaskGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'task-group/new',
        component: TaskGroupUpdateComponent,
        resolve: {
            taskGroup: TaskGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'task-group/:id/edit',
        component: TaskGroupUpdateComponent,
        resolve: {
            taskGroup: TaskGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskGroups'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const taskGroupPopupRoute: Routes = [
    {
        path: 'task-group/:id/delete',
        component: TaskGroupDeletePopupComponent,
        resolve: {
            taskGroup: TaskGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
