import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from '../../shared';
import {
    ActivityParticipationService,
    ActivityParticipationPopupService,
    ActivityParticipationComponent,
    ActivityParticipationDetailComponent,
    ActivityParticipationDialogComponent,
    ActivityParticipationPopupComponent,
    ActivityParticipationDeletePopupComponent,
    ActivityParticipationDeleteDialogComponent,
    activityParticipationRoute,
    activityParticipationPopupRoute,
    ActivityParticipationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...activityParticipationRoute,
    ...activityParticipationPopupRoute,
];

@NgModule({
    imports: [
        AitpapiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ActivityParticipationComponent,
        ActivityParticipationDetailComponent,
        ActivityParticipationDialogComponent,
        ActivityParticipationDeleteDialogComponent,
        ActivityParticipationPopupComponent,
        ActivityParticipationDeletePopupComponent,
    ],
    entryComponents: [
        ActivityParticipationComponent,
        ActivityParticipationDialogComponent,
        ActivityParticipationPopupComponent,
        ActivityParticipationDeleteDialogComponent,
        ActivityParticipationDeletePopupComponent,
    ],
    providers: [
        ActivityParticipationService,
        ActivityParticipationPopupService,
        ActivityParticipationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiActivityParticipationModule {}
