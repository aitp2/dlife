import { NgModule } from '@angular/core';

import { AitpapiSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [AitpapiSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [AitpapiSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class AitpapiSharedCommonModule {}
