import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from '../../shared';
import {
    ClockinSummaryService,
    ClockinSummaryPopupService,
    ClockinSummaryComponent,
    ClockinSummaryDetailComponent,
    ClockinSummaryDialogComponent,
    ClockinSummaryPopupComponent,
    ClockinSummaryDeletePopupComponent,
    ClockinSummaryDeleteDialogComponent,
    clockinSummaryRoute,
    clockinSummaryPopupRoute,
    ClockinSummaryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...clockinSummaryRoute,
    ...clockinSummaryPopupRoute,
];

@NgModule({
    imports: [
        AitpapiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ClockinSummaryComponent,
        ClockinSummaryDetailComponent,
        ClockinSummaryDialogComponent,
        ClockinSummaryDeleteDialogComponent,
        ClockinSummaryPopupComponent,
        ClockinSummaryDeletePopupComponent,
    ],
    entryComponents: [
        ClockinSummaryComponent,
        ClockinSummaryDialogComponent,
        ClockinSummaryPopupComponent,
        ClockinSummaryDeleteDialogComponent,
        ClockinSummaryDeletePopupComponent,
    ],
    providers: [
        ClockinSummaryService,
        ClockinSummaryPopupService,
        ClockinSummaryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiClockinSummaryModule {}
