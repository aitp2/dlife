/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { ActivityParticipationUpdateComponent } from 'app/entities/activity-participation/activity-participation-update.component';
import { ActivityParticipationService } from 'app/entities/activity-participation/activity-participation.service';
import { ActivityParticipation } from 'app/shared/model/activity-participation.model';

describe('Component Tests', () => {
    describe('ActivityParticipation Management Update Component', () => {
        let comp: ActivityParticipationUpdateComponent;
        let fixture: ComponentFixture<ActivityParticipationUpdateComponent>;
        let service: ActivityParticipationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ActivityParticipationUpdateComponent]
            })
                .overrideTemplate(ActivityParticipationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ActivityParticipationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActivityParticipationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ActivityParticipation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.activityParticipation = entity;
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
                    const entity = new ActivityParticipation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.activityParticipation = entity;
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
