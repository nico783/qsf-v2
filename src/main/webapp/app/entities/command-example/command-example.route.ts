import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommandExample } from 'app/shared/model/command-example.model';
import { CommandExampleService } from './command-example.service';
import { CommandExampleComponent } from './command-example.component';
import { CommandExampleDetailComponent } from './command-example-detail.component';
import { CommandExampleUpdateComponent } from './command-example-update.component';
import { CommandExampleDeletePopupComponent } from './command-example-delete-dialog.component';
import { ICommandExample } from 'app/shared/model/command-example.model';

@Injectable({ providedIn: 'root' })
export class CommandExampleResolve implements Resolve<ICommandExample> {
  constructor(private service: CommandExampleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommandExample> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CommandExample>) => response.ok),
        map((commandExample: HttpResponse<CommandExample>) => commandExample.body)
      );
    }
    return of(new CommandExample());
  }
}

export const commandExampleRoute: Routes = [
  {
    path: '',
    component: CommandExampleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'CommandExamples'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CommandExampleDetailComponent,
    resolve: {
      commandExample: CommandExampleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CommandExamples'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CommandExampleUpdateComponent,
    resolve: {
      commandExample: CommandExampleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CommandExamples'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CommandExampleUpdateComponent,
    resolve: {
      commandExample: CommandExampleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CommandExamples'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const commandExamplePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CommandExampleDeletePopupComponent,
    resolve: {
      commandExample: CommandExampleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CommandExamples'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
