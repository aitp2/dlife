import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AitpapiWechatUserModule } from './wechat-user/wechat-user.module';
import { AitpapiFollowModule } from './follow/follow.module';
import { AitpapiRecipeModule } from './recipe/recipe.module';
import { AitpapiImageModule } from './image/image.module';
import { AitpapiRecipeOrderModule } from './recipe-order/recipe-order.module';
import { AitpapiEvaluateModule } from './evaluate/evaluate.module';
import { AitpapiPinFanActivityModule } from './pin-fan-activity/pin-fan-activity.module';
import { AitpapiAttendeeModule } from './attendee/attendee.module';
import { AitpapiRatesModule } from './rates/rates.module';
import { AitpapiPinfanPicsModule } from './pinfan-pics/pinfan-pics.module';
import { AitpapiFitnessActivityModule } from './fitness-activity/fitness-activity.module';
import { AitpapiActivityParticipationModule } from './activity-participation/activity-participation.module';
import { AitpapiClockInModule } from './clock-in/clock-in.module';
import { AitpapiPicsModule } from './pics/pics.module';
import { AitpapiClockinSummaryModule } from './clockin-summary/clockin-summary.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AitpapiWechatUserModule,
        AitpapiFollowModule,
        AitpapiRecipeModule,
        AitpapiImageModule,
        AitpapiRecipeOrderModule,
        AitpapiEvaluateModule,
        AitpapiPinFanActivityModule,
        AitpapiAttendeeModule,
        AitpapiRatesModule,
        AitpapiPinfanPicsModule,
        AitpapiFitnessActivityModule,
        AitpapiActivityParticipationModule,
        AitpapiClockInModule,
        AitpapiPicsModule,
        AitpapiClockinSummaryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiEntityModule {}
