/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { FitnessActivityDetailComponent } from 'app/entities/fitness-activity/fitness-activity-detail.component';
import { FitnessActivity } from 'app/shared/model/fitness-activity.model';

describe('Component Tests', () => {
  describe('FitnessActivity Management Detail Component', () => {
    let comp: FitnessActivityDetailComponent;
    let fixture: ComponentFixture<FitnessActivityDetailComponent>;
    const route = ({ data: of({ fitnessActivity: new FitnessActivity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AitpapiTestModule],
        declarations: [FitnessActivityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FitnessActivityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FitnessActivityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fitnessActivity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
