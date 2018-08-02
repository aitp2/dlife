import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
    EventMessageComponent,
    EventMessageDetailComponent,
    EventMessageUpdateComponent,
    EventMessageDeletePopupComponent,
    EventMessageDeleteDialogComponent,
    eventMessageRoute,
    eventMessagePopupRoute
} from './';

const ENTITY_STATES = [...eventMessageRoute, ...eventMessagePopupRoute];

@NgModule({
    imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EventMessageComponent,
        EventMessageDetailComponent,
        EventMessageUpdateComponent,
        EventMessageDeleteDialogComponent,
        EventMessageDeletePopupComponent
    ],
    entryComponents: [
        EventMessageComponent,
        EventMessageUpdateComponent,
        EventMessageDeleteDialogComponent,
        EventMessageDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiEventMessageModule {}
