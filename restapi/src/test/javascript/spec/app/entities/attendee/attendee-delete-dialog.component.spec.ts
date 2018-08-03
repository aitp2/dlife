/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { AttendeeDeleteDialogComponent } from 'app/entities/attendee/attendee-delete-dialog.component';
import { AttendeeService } from 'app/entities/attendee/attendee.service';

describe('Component Tests', () => {
    describe('Attendee Management Delete Component', () => {
        let comp: AttendeeDeleteDialogComponent;
        let fixture: ComponentFixture<AttendeeDeleteDialogComponent>;
        let service: AttendeeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [AttendeeDeleteDialogComponent]
            })
                .overrideTemplate(AttendeeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AttendeeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AttendeeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
