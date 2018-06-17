/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { FitnessActivityDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/fitness-activity/fitness-activity-delete-dialog.component';
import { FitnessActivityService } from '../../../../../../main/webapp/app/entities/fitness-activity/fitness-activity.service';

describe('Component Tests', () => {

    describe('FitnessActivity Management Delete Component', () => {
        let comp: FitnessActivityDeleteDialogComponent;
        let fixture: ComponentFixture<FitnessActivityDeleteDialogComponent>;
        let service: FitnessActivityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [FitnessActivityDeleteDialogComponent],
                providers: [
                    FitnessActivityService
                ]
            })
            .overrideTemplate(FitnessActivityDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FitnessActivityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FitnessActivityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

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
