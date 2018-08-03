/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { ActivityParticipationDetailComponent } from 'app/entities/activity-participation/activity-participation-detail.component';
import { ActivityParticipation } from 'app/shared/model/activity-participation.model';

describe('Component Tests', () => {
    describe('ActivityParticipation Management Detail Component', () => {
        let comp: ActivityParticipationDetailComponent;
        let fixture: ComponentFixture<ActivityParticipationDetailComponent>;
        const route = ({ data: of({ activityParticipation: new ActivityParticipation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ActivityParticipationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ActivityParticipationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ActivityParticipationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.activityParticipation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
