import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from '../../shared';
import {
    RecipeService,
    RecipePopupService,
    RecipeComponent,
    RecipeDetailComponent,
    RecipeDialogComponent,
    RecipePopupComponent,
    RecipeDeletePopupComponent,
    RecipeDeleteDialogComponent,
    recipeRoute,
    recipePopupRoute,
    RecipeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...recipeRoute,
    ...recipePopupRoute,
];

@NgModule({
    imports: [
        AitpapiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RecipeComponent,
        RecipeDetailComponent,
        RecipeDialogComponent,
        RecipeDeleteDialogComponent,
        RecipePopupComponent,
        RecipeDeletePopupComponent,
    ],
    entryComponents: [
        RecipeComponent,
        RecipeDialogComponent,
        RecipePopupComponent,
        RecipeDeleteDialogComponent,
        RecipeDeletePopupComponent,
    ],
    providers: [
        RecipeService,
        RecipePopupService,
        RecipeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiRecipeModule {}
