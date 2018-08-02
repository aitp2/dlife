import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EventMessage } from 'app/shared/model/event-message.model';
import { EventMessageService } from './event-message.service';
import { EventMessageComponent } from './event-message.component';
import { EventMessageDetailComponent } from './event-message-detail.component';
import { EventMessageUpdateComponent } from './event-message-update.component';
import { EventMessageDeletePopupComponent } from './event-message-delete-dialog.component';
import { IEventMessage } from 'app/shared/model/event-message.model';

@Injectable({ providedIn: 'root' })
export class EventMessageResolve implements Resolve<IEventMessage> {
    constructor(private service: EventMessageService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((eventMessage: HttpResponse<EventMessage>) => eventMessage.body));
        }
        return of(new EventMessage());
    }
}

export const eventMessageRoute: Routes = [
    {
        path: 'event-message',
        component: EventMessageComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'EventMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'event-message/:id/view',
        component: EventMessageDetailComponent,
        resolve: {
            eventMessage: EventMessageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EventMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'event-message/new',
        component: EventMessageUpdateComponent,
        resolve: {
            eventMessage: EventMessageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EventMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'event-message/:id/edit',
        component: EventMessageUpdateComponent,
        resolve: {
            eventMessage: EventMessageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EventMessages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const eventMessagePopupRoute: Routes = [
    {
        path: 'event-message/:id/delete',
        component: EventMessageDeletePopupComponent,
        resolve: {
            eventMessage: EventMessageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EventMessages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
