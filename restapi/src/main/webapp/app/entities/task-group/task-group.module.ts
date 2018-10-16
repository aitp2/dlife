import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
    TaskGroupComponent,
    TaskGroupDetailComponent,
    TaskGroupUpdateComponent,
    TaskGroupDeletePopupComponent,
    TaskGroupDeleteDialogComponent,
    taskGroupRoute,
    taskGroupPopupRoute
} from './';

const ENTITY_STATES = [...taskGroupRoute, ...taskGroupPopupRoute];

@NgModule({
    imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TaskGroupComponent,
        TaskGroupDetailComponent,
        TaskGroupUpdateComponent,
        TaskGroupDeleteDialogComponent,
        TaskGroupDeletePopupComponent
    ],
    entryComponents: [TaskGroupComponent, TaskGroupUpdateComponent, TaskGroupDeleteDialogComponent, TaskGroupDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiTaskGroupModule {}
