import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from '../../shared';
import {
    FitnessActivityService,
    FitnessActivityPopupService,
    FitnessActivityComponent,
    FitnessActivityDetailComponent,
    FitnessActivityDialogComponent,
    FitnessActivityPopupComponent,
    FitnessActivityDeletePopupComponent,
    FitnessActivityDeleteDialogComponent,
    fitnessActivityRoute,
    fitnessActivityPopupRoute,
    FitnessActivityResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...fitnessActivityRoute,
    ...fitnessActivityPopupRoute,
];

@NgModule({
    imports: [
        AitpapiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FitnessActivityComponent,
        FitnessActivityDetailComponent,
        FitnessActivityDialogComponent,
        FitnessActivityDeleteDialogComponent,
        FitnessActivityPopupComponent,
        FitnessActivityDeletePopupComponent,
    ],
    entryComponents: [
        FitnessActivityComponent,
        FitnessActivityDialogComponent,
        FitnessActivityPopupComponent,
        FitnessActivityDeleteDialogComponent,
        FitnessActivityDeletePopupComponent,
    ],
    providers: [
        FitnessActivityService,
        FitnessActivityPopupService,
        FitnessActivityResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiFitnessActivityModule {}
