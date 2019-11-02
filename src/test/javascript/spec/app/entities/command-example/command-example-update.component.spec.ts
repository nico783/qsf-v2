/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qsf2TestModule } from '../../../test.module';
import { CommandExampleUpdateComponent } from 'app/entities/command-example/command-example-update.component';
import { CommandExampleService } from 'app/entities/command-example/command-example.service';
import { CommandExample } from 'app/shared/model/command-example.model';

describe('Component Tests', () => {
  describe('CommandExample Management Update Component', () => {
    let comp: CommandExampleUpdateComponent;
    let fixture: ComponentFixture<CommandExampleUpdateComponent>;
    let service: CommandExampleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qsf2TestModule],
        declarations: [CommandExampleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CommandExampleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommandExampleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommandExampleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommandExample(123);
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
        const entity = new CommandExample();
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
