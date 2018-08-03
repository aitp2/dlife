/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { ClockinSummaryDeleteDialogComponent } from 'app/entities/clockin-summary/clockin-summary-delete-dialog.component';
import { ClockinSummaryService } from 'app/entities/clockin-summary/clockin-summary.service';

describe('Component Tests', () => {
    describe('ClockinSummary Management Delete Component', () => {
        let comp: ClockinSummaryDeleteDialogComponent;
        let fixture: ComponentFixture<ClockinSummaryDeleteDialogComponent>;
        let service: ClockinSummaryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ClockinSummaryDeleteDialogComponent]
            })
                .overrideTemplate(ClockinSummaryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClockinSummaryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClockinSummaryService);
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
