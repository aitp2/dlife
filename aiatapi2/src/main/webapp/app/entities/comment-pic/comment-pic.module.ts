import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
    CommentPicComponent,
    CommentPicDetailComponent,
    CommentPicUpdateComponent,
    CommentPicDeletePopupComponent,
    CommentPicDeleteDialogComponent,
    commentPicRoute,
    commentPicPopupRoute
} from './';

const ENTITY_STATES = [...commentPicRoute, ...commentPicPopupRoute];

@NgModule({
    imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommentPicComponent,
        CommentPicDetailComponent,
        CommentPicUpdateComponent,
        CommentPicDeleteDialogComponent,
        CommentPicDeletePopupComponent
    ],
    entryComponents: [CommentPicComponent, CommentPicUpdateComponent, CommentPicDeleteDialogComponent, CommentPicDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiCommentPicModule {}
