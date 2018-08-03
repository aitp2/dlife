import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { RecipeOrder } from 'app/shared/model/recipe-order.model';
import { RecipeOrderService } from './recipe-order.service';
import { RecipeOrderComponent } from './recipe-order.component';
import { RecipeOrderDetailComponent } from './recipe-order-detail.component';
import { RecipeOrderUpdateComponent } from './recipe-order-update.component';
import { RecipeOrderDeletePopupComponent } from './recipe-order-delete-dialog.component';
import { IRecipeOrder } from 'app/shared/model/recipe-order.model';

@Injectable({ providedIn: 'root' })
export class RecipeOrderResolve implements Resolve<IRecipeOrder> {
    constructor(private service: RecipeOrderService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((recipeOrder: HttpResponse<RecipeOrder>) => recipeOrder.body));
        }
        return of(new RecipeOrder());
    }
}

export const recipeOrderRoute: Routes = [
    {
        path: 'recipe-order',
        component: RecipeOrderComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'RecipeOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipe-order/:id/view',
        component: RecipeOrderDetailComponent,
        resolve: {
            recipeOrder: RecipeOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecipeOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipe-order/new',
        component: RecipeOrderUpdateComponent,
        resolve: {
            recipeOrder: RecipeOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecipeOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipe-order/:id/edit',
        component: RecipeOrderUpdateComponent,
        resolve: {
            recipeOrder: RecipeOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecipeOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recipeOrderPopupRoute: Routes = [
    {
        path: 'recipe-order/:id/delete',
        component: RecipeOrderDeletePopupComponent,
        resolve: {
            recipeOrder: RecipeOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecipeOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
