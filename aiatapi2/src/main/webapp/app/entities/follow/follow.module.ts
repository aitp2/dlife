import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from '../../shared';
import {
    FollowService,
    FollowPopupService,
    FollowComponent,
    FollowDetailComponent,
    FollowDialogComponent,
    FollowPopupComponent,
    FollowDeletePopupComponent,
    FollowDeleteDialogComponent,
    followRoute,
    followPopupRoute,
    FollowResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...followRoute,
    ...followPopupRoute,
];

@NgModule({
    imports: [
        AitpapiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FollowComponent,
        FollowDetailComponent,
        FollowDialogComponent,
        FollowDeleteDialogComponent,
        FollowPopupComponent,
        FollowDeletePopupComponent,
    ],
    entryComponents: [
        FollowComponent,
        FollowDialogComponent,
        FollowPopupComponent,
        FollowDeleteDialogComponent,
        FollowDeletePopupComponent,
    ],
    providers: [
        FollowService,
        FollowPopupService,
        FollowResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiFollowModule {}
