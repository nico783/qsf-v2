import { NgModule } from '@angular/core';

import { Qsf2SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [Qsf2SharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [Qsf2SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class Qsf2SharedCommonModule {}
