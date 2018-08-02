import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
    ClockinSummaryComponent,
    ClockinSummaryDetailComponent,
    ClockinSummaryUpdateComponent,
    ClockinSummaryDeletePopupComponent,
    ClockinSummaryDeleteDialogComponent,
    clockinSummaryRoute,
    clockinSummaryPopupRoute
} from './';

const ENTITY_STATES = [...clockinSummaryRoute, ...clockinSummaryPopupRoute];

@NgModule({
    imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ClockinSummaryComponent,
        ClockinSummaryDetailComponent,
        ClockinSummaryUpdateComponent,
        ClockinSummaryDeleteDialogComponent,
        ClockinSummaryDeletePopupComponent
    ],
    entryComponents: [
        ClockinSummaryComponent,
        ClockinSummaryUpdateComponent,
        ClockinSummaryDeleteDialogComponent,
        ClockinSummaryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiClockinSummaryModule {}
