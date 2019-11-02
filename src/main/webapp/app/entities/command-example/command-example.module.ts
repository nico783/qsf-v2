import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Qsf2SharedModule } from 'app/shared';
import {
  CommandExampleComponent,
  CommandExampleDetailComponent,
  CommandExampleUpdateComponent,
  CommandExampleDeletePopupComponent,
  CommandExampleDeleteDialogComponent,
  commandExampleRoute,
  commandExamplePopupRoute
} from './';

const ENTITY_STATES = [...commandExampleRoute, ...commandExamplePopupRoute];

@NgModule({
  imports: [Qsf2SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CommandExampleComponent,
    CommandExampleDetailComponent,
    CommandExampleUpdateComponent,
    CommandExampleDeleteDialogComponent,
    CommandExampleDeletePopupComponent
  ],
  entryComponents: [
    CommandExampleComponent,
    CommandExampleUpdateComponent,
    CommandExampleDeleteDialogComponent,
    CommandExampleDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Qsf2CommandExampleModule {}
