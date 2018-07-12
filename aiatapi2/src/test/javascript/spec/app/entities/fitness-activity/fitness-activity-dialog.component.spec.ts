/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { FitnessActivityDialogComponent } from '../../../../../../main/webapp/app/entities/fitness-activity/fitness-activity-dialog.component';
import { FitnessActivityService } from '../../../../../../main/webapp/app/entities/fitness-activity/fitness-activity.service';
import { FitnessActivity } from '../../../../../../main/webapp/app/entities/fitness-activity/fitness-activity.model';

describe('Component Tests', () => {

    describe('FitnessActivity Management Dialog Component', () => {
        let comp: FitnessActivityDialogComponent;
        let fixture: ComponentFixture<FitnessActivityDialogComponent>;
        let service: FitnessActivityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [FitnessActivityDialogComponent],
                providers: [
                    FitnessActivityService
                ]
            })
            .overrideTemplate(FitnessActivityDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FitnessActivityDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FitnessActivityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FitnessActivity(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.fitnessActivity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'fitnessActivityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FitnessActivity();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.fitnessActivity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'fitnessActivityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
