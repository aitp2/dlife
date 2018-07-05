import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { RecipeOrderComponent } from './recipe-order.component';
import { RecipeOrderDetailComponent } from './recipe-order-detail.component';
import { RecipeOrderPopupComponent } from './recipe-order-dialog.component';
import { RecipeOrderDeletePopupComponent } from './recipe-order-delete-dialog.component';

@Injectable()
export class RecipeOrderResolvePagingParams implements Resolve<any> {

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

export const recipeOrderRoute: Routes = [
    {
        path: 'recipe-order',
        component: RecipeOrderComponent,
        resolve: {
            'pagingParams': RecipeOrderResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecipeOrders'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'recipe-order/:id',
        component: RecipeOrderDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecipeOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recipeOrderPopupRoute: Routes = [
    {
        path: 'recipe-order-new',
        component: RecipeOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecipeOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'recipe-order/:id/edit',
        component: RecipeOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecipeOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'recipe-order/:id/delete',
        component: RecipeOrderDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecipeOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
