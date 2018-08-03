/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { PinfanPicsDeleteDialogComponent } from 'app/entities/pinfan-pics/pinfan-pics-delete-dialog.component';
import { PinfanPicsService } from 'app/entities/pinfan-pics/pinfan-pics.service';

describe('Component Tests', () => {
    describe('PinfanPics Management Delete Component', () => {
        let comp: PinfanPicsDeleteDialogComponent;
        let fixture: ComponentFixture<PinfanPicsDeleteDialogComponent>;
        let service: PinfanPicsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [PinfanPicsDeleteDialogComponent]
            })
                .overrideTemplate(PinfanPicsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PinfanPicsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PinfanPicsService);
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
