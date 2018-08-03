import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
    RecipeOrderComponent,
    RecipeOrderDetailComponent,
    RecipeOrderUpdateComponent,
    RecipeOrderDeletePopupComponent,
    RecipeOrderDeleteDialogComponent,
    recipeOrderRoute,
    recipeOrderPopupRoute
} from './';

const ENTITY_STATES = [...recipeOrderRoute, ...recipeOrderPopupRoute];

@NgModule({
    imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RecipeOrderComponent,
        RecipeOrderDetailComponent,
        RecipeOrderUpdateComponent,
        RecipeOrderDeleteDialogComponent,
        RecipeOrderDeletePopupComponent
    ],
    entryComponents: [RecipeOrderComponent, RecipeOrderUpdateComponent, RecipeOrderDeleteDialogComponent, RecipeOrderDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiRecipeOrderModule {}
