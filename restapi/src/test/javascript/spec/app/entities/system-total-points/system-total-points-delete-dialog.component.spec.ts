/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { SystemTotalPointsDeleteDialogComponent } from 'app/entities/system-total-points/system-total-points-delete-dialog.component';
import { SystemTotalPointsService } from 'app/entities/system-total-points/system-total-points.service';

describe('Component Tests', () => {
    describe('SystemTotalPoints Management Delete Component', () => {
        let comp: SystemTotalPointsDeleteDialogComponent;
        let fixture: ComponentFixture<SystemTotalPointsDeleteDialogComponent>;
        let service: SystemTotalPointsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [SystemTotalPointsDeleteDialogComponent]
            })
                .overrideTemplate(SystemTotalPointsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SystemTotalPointsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SystemTotalPointsService);
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
