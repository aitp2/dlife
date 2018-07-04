/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { ClockInDialogComponent } from '../../../../../../main/webapp/app/entities/clock-in/clock-in-dialog.component';
import { ClockInService } from '../../../../../../main/webapp/app/entities/clock-in/clock-in.service';
import { ClockIn } from '../../../../../../main/webapp/app/entities/clock-in/clock-in.model';
import { ActivityParticipationService } from '../../../../../../main/webapp/app/entities/activity-participation';

describe('Component Tests', () => {

    describe('ClockIn Management Dialog Component', () => {
        let comp: ClockInDialogComponent;
        let fixture: ComponentFixture<ClockInDialogComponent>;
        let service: ClockInService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ClockInDialogComponent],
                providers: [
                    ActivityParticipationService,
                    ClockInService
                ]
            })
            .overrideTemplate(ClockInDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClockInDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClockInService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ClockIn(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.clockIn = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'clockInListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ClockIn();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.clockIn = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'clockInListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
