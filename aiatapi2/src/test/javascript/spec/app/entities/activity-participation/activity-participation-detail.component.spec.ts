/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { AitpapiTestModule } from '../../../test.module';
import { ActivityParticipationDetailComponent } from '../../../../../../main/webapp/app/entities/activity-participation/activity-participation-detail.component';
import { ActivityParticipationService } from '../../../../../../main/webapp/app/entities/activity-participation/activity-participation.service';
import { ActivityParticipation } from '../../../../../../main/webapp/app/entities/activity-participation/activity-participation.model';

describe('Component Tests', () => {

    describe('ActivityParticipation Management Detail Component', () => {
        let comp: ActivityParticipationDetailComponent;
        let fixture: ComponentFixture<ActivityParticipationDetailComponent>;
        let service: ActivityParticipationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ActivityParticipationDetailComponent],
                providers: [
                    ActivityParticipationService
                ]
            })
            .overrideTemplate(ActivityParticipationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ActivityParticipationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActivityParticipationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new ActivityParticipation(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.activityParticipation).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
