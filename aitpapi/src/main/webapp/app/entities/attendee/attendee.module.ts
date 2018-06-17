import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from '../../shared';
import {
    AttendeeService,
    AttendeePopupService,
    AttendeeComponent,
    AttendeeDetailComponent,
    AttendeeDialogComponent,
    AttendeePopupComponent,
    AttendeeDeletePopupComponent,
    AttendeeDeleteDialogComponent,
    attendeeRoute,
    attendeePopupRoute,
    AttendeeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...attendeeRoute,
    ...attendeePopupRoute,
];

@NgModule({
    imports: [
        AitpapiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AttendeeComponent,
        AttendeeDetailComponent,
        AttendeeDialogComponent,
        AttendeeDeleteDialogComponent,
        AttendeePopupComponent,
        AttendeeDeletePopupComponent,
    ],
    entryComponents: [
        AttendeeComponent,
        AttendeeDialogComponent,
        AttendeePopupComponent,
        AttendeeDeleteDialogComponent,
        AttendeeDeletePopupComponent,
    ],
    providers: [
        AttendeeService,
        AttendeePopupService,
        AttendeeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiAttendeeModule {}
