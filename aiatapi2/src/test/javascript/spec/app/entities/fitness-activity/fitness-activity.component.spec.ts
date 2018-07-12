/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AitpapiTestModule } from '../../../test.module';
import { FitnessActivityComponent } from '../../../../../../main/webapp/app/entities/fitness-activity/fitness-activity.component';
import { FitnessActivityService } from '../../../../../../main/webapp/app/entities/fitness-activity/fitness-activity.service';
import { FitnessActivity } from '../../../../../../main/webapp/app/entities/fitness-activity/fitness-activity.model';

describe('Component Tests', () => {

    describe('FitnessActivity Management Component', () => {
        let comp: FitnessActivityComponent;
        let fixture: ComponentFixture<FitnessActivityComponent>;
        let service: FitnessActivityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [FitnessActivityComponent],
                providers: [
                    FitnessActivityService
                ]
            })
            .overrideTemplate(FitnessActivityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FitnessActivityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FitnessActivityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new FitnessActivity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.fitnessActivities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
