import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
    PinFanActivityComponent,
    PinFanActivityDetailComponent,
    PinFanActivityUpdateComponent,
    PinFanActivityDeletePopupComponent,
    PinFanActivityDeleteDialogComponent,
    pinFanActivityRoute,
    pinFanActivityPopupRoute
} from './';

const ENTITY_STATES = [...pinFanActivityRoute, ...pinFanActivityPopupRoute];

@NgModule({
    imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PinFanActivityComponent,
        PinFanActivityDetailComponent,
        PinFanActivityUpdateComponent,
        PinFanActivityDeleteDialogComponent,
        PinFanActivityDeletePopupComponent
    ],
    entryComponents: [
        PinFanActivityComponent,
        PinFanActivityUpdateComponent,
        PinFanActivityDeleteDialogComponent,
        PinFanActivityDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiPinFanActivityModule {}
