import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PeopledirectoryTestModule } from '../../../test.module';
import { NameComponent } from 'app/entities/name/name.component';
import { NameService } from 'app/entities/name/name.service';
import { Name } from 'app/shared/model/name.model';

describe('Component Tests', () => {
  describe('Name Management Component', () => {
    let comp: NameComponent;
    let fixture: ComponentFixture<NameComponent>;
    let service: NameService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PeopledirectoryTestModule],
        declarations: [NameComponent]
      })
        .overrideTemplate(NameComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NameComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NameService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Name(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.names && comp.names[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
