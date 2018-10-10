import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
    SystemTotalPointsComponent,
    SystemTotalPointsDetailComponent,
    SystemTotalPointsUpdateComponent,
    SystemTotalPointsDeletePopupComponent,
    SystemTotalPointsDeleteDialogComponent,
    systemTotalPointsRoute,
    systemTotalPointsPopupRoute
} from './';

const ENTITY_STATES = [...systemTotalPointsRoute, ...systemTotalPointsPopupRoute];

@NgModule({
    imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SystemTotalPointsComponent,
        SystemTotalPointsDetailComponent,
        SystemTotalPointsUpdateComponent,
        SystemTotalPointsDeleteDialogComponent,
        SystemTotalPointsDeletePopupComponent
    ],
    entryComponents: [
        SystemTotalPointsComponent,
        SystemTotalPointsUpdateComponent,
        SystemTotalPointsDeleteDialogComponent,
        SystemTotalPointsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiSystemTotalPointsModule {}
