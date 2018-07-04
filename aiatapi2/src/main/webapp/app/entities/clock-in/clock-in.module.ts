import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from '../../shared';
import {
    ClockInService,
    ClockInPopupService,
    ClockInComponent,
    ClockInDetailComponent,
    ClockInDialogComponent,
    ClockInPopupComponent,
    ClockInDeletePopupComponent,
    ClockInDeleteDialogComponent,
    clockInRoute,
    clockInPopupRoute,
    ClockInResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...clockInRoute,
    ...clockInPopupRoute,
];

@NgModule({
    imports: [
        AitpapiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ClockInComponent,
        ClockInDetailComponent,
        ClockInDialogComponent,
        ClockInDeleteDialogComponent,
        ClockInPopupComponent,
        ClockInDeletePopupComponent,
    ],
    entryComponents: [
        ClockInComponent,
        ClockInDialogComponent,
        ClockInPopupComponent,
        ClockInDeleteDialogComponent,
        ClockInDeletePopupComponent,
    ],
    providers: [
        ClockInService,
        ClockInPopupService,
        ClockInResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiClockInModule {}
