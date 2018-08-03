import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
    PinfanPicsComponent,
    PinfanPicsDetailComponent,
    PinfanPicsUpdateComponent,
    PinfanPicsDeletePopupComponent,
    PinfanPicsDeleteDialogComponent,
    pinfanPicsRoute,
    pinfanPicsPopupRoute
} from './';

const ENTITY_STATES = [...pinfanPicsRoute, ...pinfanPicsPopupRoute];

@NgModule({
    imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PinfanPicsComponent,
        PinfanPicsDetailComponent,
        PinfanPicsUpdateComponent,
        PinfanPicsDeleteDialogComponent,
        PinfanPicsDeletePopupComponent
    ],
    entryComponents: [PinfanPicsComponent, PinfanPicsUpdateComponent, PinfanPicsDeleteDialogComponent, PinfanPicsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiPinfanPicsModule {}
