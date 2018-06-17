import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AitpapiSharedModule } from '../../shared';
import {
    RatesService,
    RatesPopupService,
    RatesComponent,
    RatesDetailComponent,
    RatesDialogComponent,
    RatesPopupComponent,
    RatesDeletePopupComponent,
    RatesDeleteDialogComponent,
    ratesRoute,
    ratesPopupRoute,
    RatesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...ratesRoute,
    ...ratesPopupRoute,
];

@NgModule({
    imports: [
        AitpapiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RatesComponent,
        RatesDetailComponent,
        RatesDialogComponent,
        RatesDeleteDialogComponent,
        RatesPopupComponent,
        RatesDeletePopupComponent,
    ],
    entryComponents: [
        RatesComponent,
        RatesDialogComponent,
        RatesPopupComponent,
        RatesDeleteDialogComponent,
        RatesDeletePopupComponent,
    ],
    providers: [
        RatesService,
        RatesPopupService,
        RatesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AitpapiRatesModule {}
