import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
    UserTaskComponent,
    UserTaskDetailComponent,
    UserTaskUpdateComponent,
    UserTaskDeletePopupComponent,
    UserTaskDeleteDialogComponent,
    userTaskRoute,
    userTaskPopupRoute
} from './';

const ENTITY_STATES = [...userTaskRoute, ...userTaskPopupRoute];

@NgModule({
    imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserTaskComponent,
        UserTaskDetailComponent,
        UserTaskUpdateComponent,
        UserTaskDeleteDialogComponent,
        UserTaskDeletePopupComponent
    ],
    entryComponents: [UserTaskComponent, UserTaskUpdateComponent, UserTaskDeleteDialogComponent, UserTaskDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiUserTaskModule {}
