import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { RecipeComponent } from './recipe.component';
import { RecipeDetailComponent } from './recipe-detail.component';
import { RecipePopupComponent } from './recipe-dialog.component';
import { RecipeDeletePopupComponent } from './recipe-delete-dialog.component';

@Injectable()
export class RecipeResolvePagingParams implements Resolve<any> {

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

export const recipeRoute: Routes = [
    {
        path: 'recipe',
        component: RecipeComponent,
        resolve: {
            'pagingParams': RecipeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'recipe/:id',
        component: RecipeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recipePopupRoute: Routes = [
    {
        path: 'recipe-new',
        component: RecipePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'recipe/:id/edit',
        component: RecipePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'recipe/:id/delete',
        component: RecipeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
