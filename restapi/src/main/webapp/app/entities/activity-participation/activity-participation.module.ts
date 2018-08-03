import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
    ActivityParticipationComponent,
    ActivityParticipationDetailComponent,
    ActivityParticipationUpdateComponent,
    ActivityParticipationDeletePopupComponent,
    ActivityParticipationDeleteDialogComponent,
    activityParticipationRoute,
    activityParticipationPopupRoute
} from './';

const ENTITY_STATES = [...activityParticipationRoute, ...activityParticipationPopupRoute];

@NgModule({
    imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ActivityParticipationComponent,
        ActivityParticipationDetailComponent,
        ActivityParticipationUpdateComponent,
        ActivityParticipationDeleteDialogComponent,
        ActivityParticipationDeletePopupComponent
    ],
    entryComponents: [
        ActivityParticipationComponent,
        ActivityParticipationUpdateComponent,
        ActivityParticipationDeleteDialogComponent,
        ActivityParticipationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiActivityParticipationModule {}
