import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
    WechatUserComponent,
    WechatUserDetailComponent,
    WechatUserUpdateComponent,
    WechatUserDeletePopupComponent,
    WechatUserDeleteDialogComponent,
    wechatUserRoute,
    wechatUserPopupRoute
} from './';

const ENTITY_STATES = [...wechatUserRoute, ...wechatUserPopupRoute];

@NgModule({
    imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WechatUserComponent,
        WechatUserDetailComponent,
        WechatUserUpdateComponent,
        WechatUserDeleteDialogComponent,
        WechatUserDeletePopupComponent
    ],
    entryComponents: [WechatUserComponent, WechatUserUpdateComponent, WechatUserDeleteDialogComponent, WechatUserDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiWechatUserModule {}
