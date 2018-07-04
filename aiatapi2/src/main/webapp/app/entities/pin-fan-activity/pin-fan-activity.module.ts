import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from '../../shared';
import {
    PinFanActivityService,
    PinFanActivityPopupService,
    PinFanActivityComponent,
    PinFanActivityDetailComponent,
    PinFanActivityDialogComponent,
    PinFanActivityPopupComponent,
    PinFanActivityDeletePopupComponent,
    PinFanActivityDeleteDialogComponent,
    pinFanActivityRoute,
    pinFanActivityPopupRoute,
    PinFanActivityResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...pinFanActivityRoute,
    ...pinFanActivityPopupRoute,
];

@NgModule({
    imports: [
        AitpapiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PinFanActivityComponent,
        PinFanActivityDetailComponent,
        PinFanActivityDialogComponent,
        PinFanActivityDeleteDialogComponent,
        PinFanActivityPopupComponent,
        PinFanActivityDeletePopupComponent,
    ],
    entryComponents: [
        PinFanActivityComponent,
        PinFanActivityDialogComponent,
        PinFanActivityPopupComponent,
        PinFanActivityDeleteDialogComponent,
        PinFanActivityDeletePopupComponent,
    ],
    providers: [
        PinFanActivityService,
        PinFanActivityPopupService,
        PinFanActivityResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiPinFanActivityModule {}
