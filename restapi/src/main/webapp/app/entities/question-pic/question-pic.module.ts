import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
    QuestionPicComponent,
    QuestionPicDetailComponent,
    QuestionPicUpdateComponent,
    QuestionPicDeletePopupComponent,
    QuestionPicDeleteDialogComponent,
    questionPicRoute,
    questionPicPopupRoute
} from './';

const ENTITY_STATES = [...questionPicRoute, ...questionPicPopupRoute];

@NgModule({
    imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QuestionPicComponent,
        QuestionPicDetailComponent,
        QuestionPicUpdateComponent,
        QuestionPicDeleteDialogComponent,
        QuestionPicDeletePopupComponent
    ],
    entryComponents: [QuestionPicComponent, QuestionPicUpdateComponent, QuestionPicDeleteDialogComponent, QuestionPicDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiQuestionPicModule {}
