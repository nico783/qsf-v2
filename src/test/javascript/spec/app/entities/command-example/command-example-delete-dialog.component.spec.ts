/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qsf2TestModule } from '../../../test.module';
import { CommandExampleDeleteDialogComponent } from 'app/entities/command-example/command-example-delete-dialog.component';
import { CommandExampleService } from 'app/entities/command-example/command-example.service';

describe('Component Tests', () => {
  describe('CommandExample Management Delete Component', () => {
    let comp: CommandExampleDeleteDialogComponent;
    let fixture: ComponentFixture<CommandExampleDeleteDialogComponent>;
    let service: CommandExampleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qsf2TestModule],
        declarations: [CommandExampleDeleteDialogComponent]
      })
        .overrideTemplate(CommandExampleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommandExampleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommandExampleService);
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
