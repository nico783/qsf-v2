/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qsf2TestModule } from '../../../test.module';
import { ReceiptExampleDetailComponent } from 'app/entities/receipt-example/receipt-example-detail.component';
import { ReceiptExample } from 'app/shared/model/receipt-example.model';

describe('Component Tests', () => {
  describe('ReceiptExample Management Detail Component', () => {
    let comp: ReceiptExampleDetailComponent;
    let fixture: ComponentFixture<ReceiptExampleDetailComponent>;
    const route = ({ data: of({ receiptExample: new ReceiptExample(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qsf2TestModule],
        declarations: [ReceiptExampleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReceiptExampleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReceiptExampleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.receiptExample).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
