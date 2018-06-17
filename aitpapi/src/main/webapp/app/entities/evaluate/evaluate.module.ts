import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from '../../shared';
import {
    EvaluateService,
    EvaluatePopupService,
    EvaluateComponent,
    EvaluateDetailComponent,
    EvaluateDialogComponent,
    EvaluatePopupComponent,
    EvaluateDeletePopupComponent,
    EvaluateDeleteDialogComponent,
    evaluateRoute,
    evaluatePopupRoute,
    EvaluateResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...evaluateRoute,
    ...evaluatePopupRoute,
];

@NgModule({
    imports: [
        AitpapiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EvaluateComponent,
        EvaluateDetailComponent,
        EvaluateDialogComponent,
        EvaluateDeleteDialogComponent,
        EvaluatePopupComponent,
        EvaluateDeletePopupComponent,
    ],
    entryComponents: [
        EvaluateComponent,
        EvaluateDialogComponent,
        EvaluatePopupComponent,
        EvaluateDeleteDialogComponent,
        EvaluateDeletePopupComponent,
    ],
    providers: [
        EvaluateService,
        EvaluatePopupService,
        EvaluateResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiEvaluateModule {}
