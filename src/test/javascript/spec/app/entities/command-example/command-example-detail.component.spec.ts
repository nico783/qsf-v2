/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qsf2TestModule } from '../../../test.module';
import { CommandExampleDetailComponent } from 'app/entities/command-example/command-example-detail.component';
import { CommandExample } from 'app/shared/model/command-example.model';

describe('Component Tests', () => {
  describe('CommandExample Management Detail Component', () => {
    let comp: CommandExampleDetailComponent;
    let fixture: ComponentFixture<CommandExampleDetailComponent>;
    const route = ({ data: of({ commandExample: new CommandExample(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qsf2TestModule],
        declarations: [CommandExampleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CommandExampleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommandExampleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commandExample).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
