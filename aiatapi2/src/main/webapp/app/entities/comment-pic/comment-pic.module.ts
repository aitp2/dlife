import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from '../../shared';
import {
    CommentPicService,
    CommentPicPopupService,
    CommentPicComponent,
    CommentPicDetailComponent,
    CommentPicDialogComponent,
    CommentPicPopupComponent,
    CommentPicDeletePopupComponent,
    CommentPicDeleteDialogComponent,
    commentPicRoute,
    commentPicPopupRoute,
    CommentPicResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...commentPicRoute,
    ...commentPicPopupRoute,
];

@NgModule({
    imports: [
        AitpapiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CommentPicComponent,
        CommentPicDetailComponent,
        CommentPicDialogComponent,
        CommentPicDeleteDialogComponent,
        CommentPicPopupComponent,
        CommentPicDeletePopupComponent,
    ],
    entryComponents: [
        CommentPicComponent,
        CommentPicDialogComponent,
        CommentPicPopupComponent,
        CommentPicDeleteDialogComponent,
        CommentPicDeletePopupComponent,
    ],
    providers: [
        CommentPicService,
        CommentPicPopupService,
        CommentPicResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiCommentPicModule {}
