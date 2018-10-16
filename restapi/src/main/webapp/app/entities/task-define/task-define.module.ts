import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
    TaskDefineComponent,
    TaskDefineDetailComponent,
    TaskDefineUpdateComponent,
    TaskDefineDeletePopupComponent,
    TaskDefineDeleteDialogComponent,
    taskDefineRoute,
    taskDefinePopupRoute
} from './';

const ENTITY_STATES = [...taskDefineRoute, ...taskDefinePopupRoute];

@NgModule({
    imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TaskDefineComponent,
        TaskDefineDetailComponent,
        TaskDefineUpdateComponent,
        TaskDefineDeleteDialogComponent,
        TaskDefineDeletePopupComponent
    ],
    entryComponents: [TaskDefineComponent, TaskDefineUpdateComponent, TaskDefineDeleteDialogComponent, TaskDefineDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiTaskDefineModule {}
