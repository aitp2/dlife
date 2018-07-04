import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from '../../shared';
import {
    PinfanPicsService,
    PinfanPicsPopupService,
    PinfanPicsComponent,
    PinfanPicsDetailComponent,
    PinfanPicsDialogComponent,
    PinfanPicsPopupComponent,
    PinfanPicsDeletePopupComponent,
    PinfanPicsDeleteDialogComponent,
    pinfanPicsRoute,
    pinfanPicsPopupRoute,
    PinfanPicsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...pinfanPicsRoute,
    ...pinfanPicsPopupRoute,
];

@NgModule({
    imports: [
        AitpapiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PinfanPicsComponent,
        PinfanPicsDetailComponent,
        PinfanPicsDialogComponent,
        PinfanPicsDeleteDialogComponent,
        PinfanPicsPopupComponent,
        PinfanPicsDeletePopupComponent,
    ],
    entryComponents: [
        PinfanPicsComponent,
        PinfanPicsDialogComponent,
        PinfanPicsPopupComponent,
        PinfanPicsDeleteDialogComponent,
        PinfanPicsDeletePopupComponent,
    ],
    providers: [
        PinfanPicsService,
        PinfanPicsPopupService,
        PinfanPicsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiPinfanPicsModule {}
