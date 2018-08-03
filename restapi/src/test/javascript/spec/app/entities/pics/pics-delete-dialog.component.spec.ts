/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { PicsDeleteDialogComponent } from 'app/entities/pics/pics-delete-dialog.component';
import { PicsService } from 'app/entities/pics/pics.service';

describe('Component Tests', () => {
    describe('Pics Management Delete Component', () => {
        let comp: PicsDeleteDialogComponent;
        let fixture: ComponentFixture<PicsDeleteDialogComponent>;
        let service: PicsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [PicsDeleteDialogComponent]
            })
                .overrideTemplate(PicsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PicsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PicsService);
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
