import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from '../../shared';
import {
    RecipeOrderService,
    RecipeOrderPopupService,
    RecipeOrderComponent,
    RecipeOrderDetailComponent,
    RecipeOrderDialogComponent,
    RecipeOrderPopupComponent,
    RecipeOrderDeletePopupComponent,
    RecipeOrderDeleteDialogComponent,
    recipeOrderRoute,
    recipeOrderPopupRoute,
    RecipeOrderResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...recipeOrderRoute,
    ...recipeOrderPopupRoute,
];

@NgModule({
    imports: [
        AitpapiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RecipeOrderComponent,
        RecipeOrderDetailComponent,
        RecipeOrderDialogComponent,
        RecipeOrderDeleteDialogComponent,
        RecipeOrderPopupComponent,
        RecipeOrderDeletePopupComponent,
    ],
    entryComponents: [
        RecipeOrderComponent,
        RecipeOrderDialogComponent,
        RecipeOrderPopupComponent,
        RecipeOrderDeleteDialogComponent,
        RecipeOrderDeletePopupComponent,
    ],
    providers: [
        RecipeOrderService,
        RecipeOrderPopupService,
        RecipeOrderResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiRecipeOrderModule {}
