import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
  ClockInComponent,
  ClockInDetailComponent,
  ClockInUpdateComponent,
  ClockInDeletePopupComponent,
  ClockInDeleteDialogComponent,
  clockInRoute,
  clockInPopupRoute
} from './';

const ENTITY_STATES = [...clockInRoute, ...clockInPopupRoute];

@NgModule({
  imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ClockInComponent,
    ClockInDetailComponent,
    ClockInUpdateComponent,
    ClockInDeleteDialogComponent,
    ClockInDeletePopupComponent
  ],
  entryComponents: [ClockInComponent, ClockInUpdateComponent, ClockInDeleteDialogComponent, ClockInDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiClockInModule {}
