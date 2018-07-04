/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { AttendeeDialogComponent } from '../../../../../../main/webapp/app/entities/attendee/attendee-dialog.component';
import { AttendeeService } from '../../../../../../main/webapp/app/entities/attendee/attendee.service';
import { Attendee } from '../../../../../../main/webapp/app/entities/attendee/attendee.model';
import { PinFanActivityService } from '../../../../../../main/webapp/app/entities/pin-fan-activity';

describe('Component Tests', () => {

    describe('Attendee Management Dialog Component', () => {
        let comp: AttendeeDialogComponent;
        let fixture: ComponentFixture<AttendeeDialogComponent>;
        let service: AttendeeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [AttendeeDialogComponent],
                providers: [
                    PinFanActivityService,
                    AttendeeService
                ]
            })
            .overrideTemplate(AttendeeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AttendeeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AttendeeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Attendee(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.attendee = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'attendeeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Attendee();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.attendee = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'attendeeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
