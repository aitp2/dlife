import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
    ThumbsUpComponent,
    ThumbsUpDetailComponent,
    ThumbsUpUpdateComponent,
    ThumbsUpDeletePopupComponent,
    ThumbsUpDeleteDialogComponent,
    thumbsUpRoute,
    thumbsUpPopupRoute
} from './';

const ENTITY_STATES = [...thumbsUpRoute, ...thumbsUpPopupRoute];

@NgModule({
    imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ThumbsUpComponent,
        ThumbsUpDetailComponent,
        ThumbsUpUpdateComponent,
        ThumbsUpDeleteDialogComponent,
        ThumbsUpDeletePopupComponent
    ],
    entryComponents: [ThumbsUpComponent, ThumbsUpUpdateComponent, ThumbsUpDeleteDialogComponent, ThumbsUpDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiThumbsUpModule {}
