import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IReceiptExample, ReceiptExample } from 'app/shared/model/receipt-example.model';
import { ReceiptExampleService } from './receipt-example.service';

@Component({
  selector: 'jhi-receipt-example-update',
  templateUrl: './receipt-example-update.component.html'
})
export class ReceiptExampleUpdateComponent implements OnInit {
  receiptExample: IReceiptExample;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    state: []
  });

  constructor(protected receiptExampleService: ReceiptExampleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ receiptExample }) => {
      this.updateForm(receiptExample);
      this.receiptExample = receiptExample;
    });
  }

  updateForm(receiptExample: IReceiptExample) {
    this.editForm.patchValue({
      id: receiptExample.id,
      state: receiptExample.state
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const receiptExample = this.createFromForm();
    if (receiptExample.id !== undefined) {
      this.subscribeToSaveResponse(this.receiptExampleService.update(receiptExample));
    } else {
      this.subscribeToSaveResponse(this.receiptExampleService.create(receiptExample));
    }
  }

  private createFromForm(): IReceiptExample {
    const entity = {
      ...new ReceiptExample(),
      id: this.editForm.get(['id']).value,
      state: this.editForm.get(['state']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReceiptExample>>) {
    result.subscribe((res: HttpResponse<IReceiptExample>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
