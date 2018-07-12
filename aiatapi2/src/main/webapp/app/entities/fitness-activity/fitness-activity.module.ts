import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from 'app/shared';
import {
  FitnessActivityComponent,
  FitnessActivityDetailComponent,
  FitnessActivityUpdateComponent,
  FitnessActivityDeletePopupComponent,
  FitnessActivityDeleteDialogComponent,
  fitnessActivityRoute,
  fitnessActivityPopupRoute
} from './';

const ENTITY_STATES = [...fitnessActivityRoute, ...fitnessActivityPopupRoute];

@NgModule({
  imports: [AitpapiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FitnessActivityComponent,
    FitnessActivityDetailComponent,
    FitnessActivityUpdateComponent,
    FitnessActivityDeleteDialogComponent,
    FitnessActivityDeletePopupComponent
  ],
  entryComponents: [
    FitnessActivityComponent,
    FitnessActivityUpdateComponent,
    FitnessActivityDeleteDialogComponent,
    FitnessActivityDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiFitnessActivityModule {}
