/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { ClockinSummaryDialogComponent } from '../../../../../../main/webapp/app/entities/clockin-summary/clockin-summary-dialog.component';
import { ClockinSummaryService } from '../../../../../../main/webapp/app/entities/clockin-summary/clockin-summary.service';
import { ClockinSummary } from '../../../../../../main/webapp/app/entities/clockin-summary/clockin-summary.model';

describe('Component Tests', () => {

    describe('ClockinSummary Management Dialog Component', () => {
        let comp: ClockinSummaryDialogComponent;
        let fixture: ComponentFixture<ClockinSummaryDialogComponent>;
        let service: ClockinSummaryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ClockinSummaryDialogComponent],
                providers: [
                    ClockinSummaryService
                ]
            })
            .overrideTemplate(ClockinSummaryDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClockinSummaryDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClockinSummaryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ClockinSummary(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.clockinSummary = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'clockinSummaryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ClockinSummary();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.clockinSummary = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'clockinSummaryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
