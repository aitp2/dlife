/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { FitnessActivityUpdateComponent } from 'app/entities/fitness-activity/fitness-activity-update.component';
import { FitnessActivityService } from 'app/entities/fitness-activity/fitness-activity.service';
import { FitnessActivity } from 'app/shared/model/fitness-activity.model';

describe('Component Tests', () => {
  describe('FitnessActivity Management Update Component', () => {
    let comp: FitnessActivityUpdateComponent;
    let fixture: ComponentFixture<FitnessActivityUpdateComponent>;
    let service: FitnessActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AitpapiTestModule],
        declarations: [FitnessActivityUpdateComponent]
      })
        .overrideTemplate(FitnessActivityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FitnessActivityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FitnessActivityService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new FitnessActivity(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.fitnessActivity = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.update).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );

      it(
        'Should call create service on save for new entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new FitnessActivity();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.fitnessActivity = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.create).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );
    });
  });
});
