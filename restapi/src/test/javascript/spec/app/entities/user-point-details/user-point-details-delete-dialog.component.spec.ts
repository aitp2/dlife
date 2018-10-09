/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { UserPointDetailsDeleteDialogComponent } from 'app/entities/user-point-details/user-point-details-delete-dialog.component';
import { UserPointDetailsService } from 'app/entities/user-point-details/user-point-details.service';

describe('Component Tests', () => {
    describe('UserPointDetails Management Delete Component', () => {
        let comp: UserPointDetailsDeleteDialogComponent;
        let fixture: ComponentFixture<UserPointDetailsDeleteDialogComponent>;
        let service: UserPointDetailsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [UserPointDetailsDeleteDialogComponent]
            })
                .overrideTemplate(UserPointDetailsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserPointDetailsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserPointDetailsService);
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
