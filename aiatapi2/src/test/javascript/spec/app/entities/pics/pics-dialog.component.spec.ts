/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { PicsDialogComponent } from '../../../../../../main/webapp/app/entities/pics/pics-dialog.component';
import { PicsService } from '../../../../../../main/webapp/app/entities/pics/pics.service';
import { Pics } from '../../../../../../main/webapp/app/entities/pics/pics.model';
import { FitnessActivityService } from '../../../../../../main/webapp/app/entities/fitness-activity';
import { ClockInService } from '../../../../../../main/webapp/app/entities/clock-in';

describe('Component Tests', () => {

    describe('Pics Management Dialog Component', () => {
        let comp: PicsDialogComponent;
        let fixture: ComponentFixture<PicsDialogComponent>;
        let service: PicsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [PicsDialogComponent],
                providers: [
                    FitnessActivityService,
                    ClockInService,
                    PicsService
                ]
            })
            .overrideTemplate(PicsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PicsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PicsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Pics(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.pics = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'picsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Pics();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.pics = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'picsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
