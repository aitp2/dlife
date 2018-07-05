import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from '../../shared';
import {
    PicsService,
    PicsPopupService,
    PicsComponent,
    PicsDetailComponent,
    PicsDialogComponent,
    PicsPopupComponent,
    PicsDeletePopupComponent,
    PicsDeleteDialogComponent,
    picsRoute,
    picsPopupRoute,
    PicsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...picsRoute,
    ...picsPopupRoute,
];

@NgModule({
    imports: [
        AitpapiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PicsComponent,
        PicsDetailComponent,
        PicsDialogComponent,
        PicsDeleteDialogComponent,
        PicsPopupComponent,
        PicsDeletePopupComponent,
    ],
    entryComponents: [
        PicsComponent,
        PicsDialogComponent,
        PicsPopupComponent,
        PicsDeleteDialogComponent,
        PicsDeletePopupComponent,
    ],
    providers: [
        PicsService,
        PicsPopupService,
        PicsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiPicsModule {}
