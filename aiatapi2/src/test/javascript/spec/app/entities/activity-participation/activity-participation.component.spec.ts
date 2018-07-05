/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { AitpapiTestModule } from '../../../test.module';
import { ActivityParticipationComponent } from '../../../../../../main/webapp/app/entities/activity-participation/activity-participation.component';
import { ActivityParticipationService } from '../../../../../../main/webapp/app/entities/activity-participation/activity-participation.service';
import { ActivityParticipation } from '../../../../../../main/webapp/app/entities/activity-participation/activity-participation.model';

describe('Component Tests', () => {

    describe('ActivityParticipation Management Component', () => {
        let comp: ActivityParticipationComponent;
        let fixture: ComponentFixture<ActivityParticipationComponent>;
        let service: ActivityParticipationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ActivityParticipationComponent],
                providers: [
                    ActivityParticipationService
                ]
            })
            .overrideTemplate(ActivityParticipationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ActivityParticipationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActivityParticipationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new ActivityParticipation(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.activityParticipations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
