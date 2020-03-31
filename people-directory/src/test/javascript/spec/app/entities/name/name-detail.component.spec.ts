import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PeopledirectoryTestModule } from '../../../test.module';
import { NameDetailComponent } from 'app/entities/name/name-detail.component';
import { Name } from 'app/shared/model/name.model';

describe('Component Tests', () => {
  describe('Name Management Detail Component', () => {
    let comp: NameDetailComponent;
    let fixture: ComponentFixture<NameDetailComponent>;
    const route = ({ data: of({ name: new Name(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PeopledirectoryTestModule],
        declarations: [NameDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NameDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NameDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load name on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.name).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
