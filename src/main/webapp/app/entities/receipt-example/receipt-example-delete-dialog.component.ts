import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReceiptExample } from 'app/shared/model/receipt-example.model';
import { ReceiptExampleService } from './receipt-example.service';

@Component({
  selector: 'jhi-receipt-example-delete-dialog',
  templateUrl: './receipt-example-delete-dialog.component.html'
})
export class ReceiptExampleDeleteDialogComponent {
  receiptExample: IReceiptExample;

  constructor(
    protected receiptExampleService: ReceiptExampleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.receiptExampleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'receiptExampleListModification',
        content: 'Deleted an receiptExample'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-receipt-example-delete-popup',
  template: ''
})
export class ReceiptExampleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ receiptExample }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ReceiptExampleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.receiptExample = receiptExample;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/receipt-example', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/receipt-example', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
