/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qsf2TestModule } from '../../../test.module';
import { ReceiptExampleDeleteDialogComponent } from 'app/entities/receipt-example/receipt-example-delete-dialog.component';
import { ReceiptExampleService } from 'app/entities/receipt-example/receipt-example.service';

describe('Component Tests', () => {
  describe('ReceiptExample Management Delete Component', () => {
    let comp: ReceiptExampleDeleteDialogComponent;
    let fixture: ComponentFixture<ReceiptExampleDeleteDialogComponent>;
    let service: ReceiptExampleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qsf2TestModule],
        declarations: [ReceiptExampleDeleteDialogComponent]
      })
        .overrideTemplate(ReceiptExampleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReceiptExampleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReceiptExampleService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
