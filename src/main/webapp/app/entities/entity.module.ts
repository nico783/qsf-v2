import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'command-example',
        loadChildren: './command-example/command-example.module#Qsf2CommandExampleModule'
      },
      {
        path: 'receipt-example',
        loadChildren: './receipt-example/receipt-example.module#Qsf2ReceiptExampleModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Qsf2EntityModule {}
