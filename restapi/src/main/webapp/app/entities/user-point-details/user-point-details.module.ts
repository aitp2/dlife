import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
    UserPointDetailsComponent,
    UserPointDetailsDetailComponent,
    UserPointDetailsUpdateComponent,
    UserPointDetailsDeletePopupComponent,
    UserPointDetailsDeleteDialogComponent,
    userPointDetailsRoute,
    userPointDetailsPopupRoute
} from './';

const ENTITY_STATES = [...userPointDetailsRoute, ...userPointDetailsPopupRoute];

@NgModule({
    imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserPointDetailsComponent,
        UserPointDetailsDetailComponent,
        UserPointDetailsUpdateComponent,
        UserPointDetailsDeleteDialogComponent,
        UserPointDetailsDeletePopupComponent
    ],
    entryComponents: [
        UserPointDetailsComponent,
        UserPointDetailsUpdateComponent,
        UserPointDetailsDeleteDialogComponent,
        UserPointDetailsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiUserPointDetailsModule {}
