import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Qsf2SharedModule } from 'app/shared';
import {
  ReceiptExampleComponent,
  ReceiptExampleDetailComponent,
  ReceiptExampleUpdateComponent,
  ReceiptExampleDeletePopupComponent,
  ReceiptExampleDeleteDialogComponent,
  receiptExampleRoute,
  receiptExamplePopupRoute
} from './';

const ENTITY_STATES = [...receiptExampleRoute, ...receiptExamplePopupRoute];

@NgModule({
  imports: [Qsf2SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ReceiptExampleComponent,
    ReceiptExampleDetailComponent,
    ReceiptExampleUpdateComponent,
    ReceiptExampleDeleteDialogComponent,
    ReceiptExampleDeletePopupComponent
  ],
  entryComponents: [
    ReceiptExampleComponent,
    ReceiptExampleUpdateComponent,
    ReceiptExampleDeleteDialogComponent,
    ReceiptExampleDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Qsf2ReceiptExampleModule {}
