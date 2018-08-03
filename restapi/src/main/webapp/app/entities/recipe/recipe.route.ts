import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Recipe } from 'app/shared/model/recipe.model';
import { RecipeService } from './recipe.service';
import { RecipeComponent } from './recipe.component';
import { RecipeDetailComponent } from './recipe-detail.component';
import { RecipeUpdateComponent } from './recipe-update.component';
import { RecipeDeletePopupComponent } from './recipe-delete-dialog.component';
import { IRecipe } from 'app/shared/model/recipe.model';

@Injectable({ providedIn: 'root' })
export class RecipeResolve implements Resolve<IRecipe> {
    constructor(private service: RecipeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((recipe: HttpResponse<Recipe>) => recipe.body));
        }
        return of(new Recipe());
    }
}

export const recipeRoute: Routes = [
    {
        path: 'recipe',
        component: RecipeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Recipes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipe/:id/view',
        component: RecipeDetailComponent,
        resolve: {
            recipe: RecipeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipe/new',
        component: RecipeUpdateComponent,
        resolve: {
            recipe: RecipeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipe/:id/edit',
        component: RecipeUpdateComponent,
        resolve: {
            recipe: RecipeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recipePopupRoute: Routes = [
    {
        path: 'recipe/:id/delete',
        component: RecipeDeletePopupComponent,
        resolve: {
            recipe: RecipeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
