import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Image } from 'app/shared/model/image.model';
import { ImageService } from './image.service';
import { ImageComponent } from './image.component';
import { ImageDetailComponent } from './image-detail.component';
import { ImageUpdateComponent } from './image-update.component';
import { ImageDeletePopupComponent } from './image-delete-dialog.component';
import { IImage } from 'app/shared/model/image.model';

@Injectable({ providedIn: 'root' })
export class ImageResolve implements Resolve<IImage> {
    constructor(private service: ImageService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((image: HttpResponse<Image>) => image.body));
        }
        return of(new Image());
    }
}

export const imageRoute: Routes = [
    {
        path: 'image',
        component: ImageComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Images'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'image/:id/view',
        component: ImageDetailComponent,
        resolve: {
            image: ImageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Images'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'image/new',
        component: ImageUpdateComponent,
        resolve: {
            image: ImageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Images'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'image/:id/edit',
        component: ImageUpdateComponent,
        resolve: {
            image: ImageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Images'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const imagePopupRoute: Routes = [
    {
        path: 'image/:id/delete',
        component: ImageDeletePopupComponent,
        resolve: {
            image: ImageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Images'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
