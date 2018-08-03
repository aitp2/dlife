import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AitpapiWechatUserModule } from './wechat-user/wechat-user.module';
import { AitpapiFollowModule } from './follow/follow.module';
import { AitpapiCommentModule } from './comment/comment.module';
import { AitpapiCommentPicModule } from './comment-pic/comment-pic.module';
import { AitpapiEventMessageModule } from './event-message/event-message.module';
import { AitpapiMessageModule } from './message/message.module';
import { AitpapiRecipeModule } from './recipe/recipe.module';
import { AitpapiImageModule } from './image/image.module';
import { AitpapiRecipeOrderModule } from './recipe-order/recipe-order.module';
import { AitpapiPinFanActivityModule } from './pin-fan-activity/pin-fan-activity.module';
import { AitpapiAttendeeModule } from './attendee/attendee.module';
import { AitpapiPinfanPicsModule } from './pinfan-pics/pinfan-pics.module';
import { AitpapiFitnessActivityModule } from './fitness-activity/fitness-activity.module';
import { AitpapiActivityParticipationModule } from './activity-participation/activity-participation.module';
import { AitpapiClockInModule } from './clock-in/clock-in.module';
import { AitpapiPicsModule } from './pics/pics.module';
import { AitpapiClockinSummaryModule } from './clockin-summary/clockin-summary.module';
import { AitpapiQuestionModule } from './question/question.module';
import { AitpapiQuestionPicModule } from './question-pic/question-pic.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AitpapiWechatUserModule,
        AitpapiFollowModule,
        AitpapiCommentModule,
        AitpapiCommentPicModule,
        AitpapiEventMessageModule,
        AitpapiMessageModule,
        AitpapiRecipeModule,
        AitpapiImageModule,
        AitpapiRecipeOrderModule,
        AitpapiPinFanActivityModule,
        AitpapiAttendeeModule,
        AitpapiPinfanPicsModule,
        AitpapiFitnessActivityModule,
        AitpapiActivityParticipationModule,
        AitpapiClockInModule,
        AitpapiPicsModule,
        AitpapiClockinSummaryModule,
        AitpapiQuestionModule,
        AitpapiQuestionPicModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiEntityModule {}
