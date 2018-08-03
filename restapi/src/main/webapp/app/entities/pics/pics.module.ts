import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
    PicsComponent,
    PicsDetailComponent,
    PicsUpdateComponent,
    PicsDeletePopupComponent,
    PicsDeleteDialogComponent,
    picsRoute,
    picsPopupRoute
} from './';

const ENTITY_STATES = [...picsRoute, ...picsPopupRoute];

@NgModule({
    imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PicsComponent, PicsDetailComponent, PicsUpdateComponent, PicsDeleteDialogComponent, PicsDeletePopupComponent],
    entryComponents: [PicsComponent, PicsUpdateComponent, PicsDeleteDialogComponent, PicsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiPicsModule {}
