import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReceiptExample } from 'app/shared/model/receipt-example.model';

@Component({
  selector: 'jhi-receipt-example-detail',
  templateUrl: './receipt-example-detail.component.html'
})
export class ReceiptExampleDetailComponent implements OnInit {
  receiptExample: IReceiptExample;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ receiptExample }) => {
      this.receiptExample = receiptExample;
    });
  }

  previousState() {
    window.history.back();
  }
}
