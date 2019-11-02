import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ReceiptExample } from 'app/shared/model/receipt-example.model';
import { ReceiptExampleService } from './receipt-example.service';
import { ReceiptExampleComponent } from './receipt-example.component';
import { ReceiptExampleDetailComponent } from './receipt-example-detail.component';
import { ReceiptExampleUpdateComponent } from './receipt-example-update.component';
import { ReceiptExampleDeletePopupComponent } from './receipt-example-delete-dialog.component';
import { IReceiptExample } from 'app/shared/model/receipt-example.model';

@Injectable({ providedIn: 'root' })
export class ReceiptExampleResolve implements Resolve<IReceiptExample> {
  constructor(private service: ReceiptExampleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReceiptExample> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ReceiptExample>) => response.ok),
        map((receiptExample: HttpResponse<ReceiptExample>) => receiptExample.body)
      );
    }
    return of(new ReceiptExample());
  }
}

export const receiptExampleRoute: Routes = [
  {
    path: '',
    component: ReceiptExampleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ReceiptExamples'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReceiptExampleDetailComponent,
    resolve: {
      receiptExample: ReceiptExampleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReceiptExamples'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReceiptExampleUpdateComponent,
    resolve: {
      receiptExample: ReceiptExampleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReceiptExamples'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReceiptExampleUpdateComponent,
    resolve: {
      receiptExample: ReceiptExampleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReceiptExamples'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const receiptExamplePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ReceiptExampleDeletePopupComponent,
    resolve: {
      receiptExample: ReceiptExampleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReceiptExamples'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
