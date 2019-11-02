import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommandExample, CommandExample } from 'app/shared/model/command-example.model';
import { CommandExampleService } from './command-example.service';
import { IReceiptExample } from 'app/shared/model/receipt-example.model';
import { ReceiptExampleService } from 'app/entities/receipt-example';

@Component({
  selector: 'jhi-command-example-update',
  templateUrl: './command-example-update.component.html'
})
export class CommandExampleUpdateComponent implements OnInit {
  commandExample: ICommandExample;
  isSaving: boolean;

  receiptexamples: IReceiptExample[];

  editForm = this.fb.group({
    id: [],
    description: [],
    receiptExampleId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected commandExampleService: CommandExampleService,
    protected receiptExampleService: ReceiptExampleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ commandExample }) => {
      this.updateForm(commandExample);
      this.commandExample = commandExample;
    });
    this.receiptExampleService
      .query({ 'commandExampleId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IReceiptExample[]>) => mayBeOk.ok),
        map((response: HttpResponse<IReceiptExample[]>) => response.body)
      )
      .subscribe(
        (res: IReceiptExample[]) => {
          if (!this.commandExample.receiptExampleId) {
            this.receiptexamples = res;
          } else {
            this.receiptExampleService
              .find(this.commandExample.receiptExampleId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IReceiptExample>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IReceiptExample>) => subResponse.body)
              )
              .subscribe(
                (subRes: IReceiptExample) => (this.receiptexamples = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(commandExample: ICommandExample) {
    this.editForm.patchValue({
      id: commandExample.id,
      description: commandExample.description,
      receiptExampleId: commandExample.receiptExampleId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const commandExample = this.createFromForm();
    if (commandExample.id !== undefined) {
      this.subscribeToSaveResponse(this.commandExampleService.update(commandExample));
    } else {
      this.subscribeToSaveResponse(this.commandExampleService.create(commandExample));
    }
  }

  private createFromForm(): ICommandExample {
    const entity = {
      ...new CommandExample(),
      id: this.editForm.get(['id']).value,
      description: this.editForm.get(['description']).value,
      receiptExampleId: this.editForm.get(['receiptExampleId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommandExample>>) {
    result.subscribe((res: HttpResponse<ICommandExample>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackReceiptExampleById(index: number, item: IReceiptExample) {
    return item.id;
  }
}
