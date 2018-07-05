/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { ActivityParticipationDialogComponent } from '../../../../../../main/webapp/app/entities/activity-participation/activity-participation-dialog.component';
import { ActivityParticipationService } from '../../../../../../main/webapp/app/entities/activity-participation/activity-participation.service';
import { ActivityParticipation } from '../../../../../../main/webapp/app/entities/activity-participation/activity-participation.model';
import { FitnessActivityService } from '../../../../../../main/webapp/app/entities/fitness-activity';

describe('Component Tests', () => {

    describe('ActivityParticipation Management Dialog Component', () => {
        let comp: ActivityParticipationDialogComponent;
        let fixture: ComponentFixture<ActivityParticipationDialogComponent>;
        let service: ActivityParticipationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ActivityParticipationDialogComponent],
                providers: [
                    FitnessActivityService,
                    ActivityParticipationService
                ]
            })
            .overrideTemplate(ActivityParticipationDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ActivityParticipationDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActivityParticipationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ActivityParticipation(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.activityParticipation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'activityParticipationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ActivityParticipation();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.activityParticipation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'activityParticipationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
