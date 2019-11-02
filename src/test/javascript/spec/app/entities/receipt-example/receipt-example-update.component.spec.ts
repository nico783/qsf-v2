/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qsf2TestModule } from '../../../test.module';
import { ReceiptExampleUpdateComponent } from 'app/entities/receipt-example/receipt-example-update.component';
import { ReceiptExampleService } from 'app/entities/receipt-example/receipt-example.service';
import { ReceiptExample } from 'app/shared/model/receipt-example.model';

describe('Component Tests', () => {
  describe('ReceiptExample Management Update Component', () => {
    let comp: ReceiptExampleUpdateComponent;
    let fixture: ComponentFixture<ReceiptExampleUpdateComponent>;
    let service: ReceiptExampleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qsf2TestModule],
        declarations: [ReceiptExampleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReceiptExampleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReceiptExampleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReceiptExampleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReceiptExample(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReceiptExample();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
