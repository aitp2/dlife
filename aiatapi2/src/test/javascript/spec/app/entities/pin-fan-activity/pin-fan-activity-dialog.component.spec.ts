/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { PinFanActivityDialogComponent } from '../../../../../../main/webapp/app/entities/pin-fan-activity/pin-fan-activity-dialog.component';
import { PinFanActivityService } from '../../../../../../main/webapp/app/entities/pin-fan-activity/pin-fan-activity.service';
import { PinFanActivity } from '../../../../../../main/webapp/app/entities/pin-fan-activity/pin-fan-activity.model';

describe('Component Tests', () => {

    describe('PinFanActivity Management Dialog Component', () => {
        let comp: PinFanActivityDialogComponent;
        let fixture: ComponentFixture<PinFanActivityDialogComponent>;
        let service: PinFanActivityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [PinFanActivityDialogComponent],
                providers: [
                    PinFanActivityService
                ]
            })
            .overrideTemplate(PinFanActivityDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PinFanActivityDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PinFanActivityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PinFanActivity(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.pinFanActivity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'pinFanActivityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PinFanActivity();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.pinFanActivity = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'pinFanActivityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
