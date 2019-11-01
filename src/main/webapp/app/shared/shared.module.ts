import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { Qsf2SharedLibsModule, Qsf2SharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [Qsf2SharedLibsModule, Qsf2SharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [Qsf2SharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Qsf2SharedModule {
  static forRoot() {
    return {
      ngModule: Qsf2SharedModule
    };
  }
}
