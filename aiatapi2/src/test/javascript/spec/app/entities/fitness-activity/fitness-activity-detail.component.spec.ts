/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { AitpapiTestModule } from '../../../test.module';
import { FitnessActivityDetailComponent } from '../../../../../../main/webapp/app/entities/fitness-activity/fitness-activity-detail.component';
import { FitnessActivityService } from '../../../../../../main/webapp/app/entities/fitness-activity/fitness-activity.service';
import { FitnessActivity } from '../../../../../../main/webapp/app/entities/fitness-activity/fitness-activity.model';

describe('Component Tests', () => {

    describe('FitnessActivity Management Detail Component', () => {
        let comp: FitnessActivityDetailComponent;
        let fixture: ComponentFixture<FitnessActivityDetailComponent>;
        let service: FitnessActivityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [FitnessActivityDetailComponent],
                providers: [
                    FitnessActivityService
                ]
            })
            .overrideTemplate(FitnessActivityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FitnessActivityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FitnessActivityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new FitnessActivity(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.fitnessActivity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
