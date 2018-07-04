/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { PinfanPicsDialogComponent } from '../../../../../../main/webapp/app/entities/pinfan-pics/pinfan-pics-dialog.component';
import { PinfanPicsService } from '../../../../../../main/webapp/app/entities/pinfan-pics/pinfan-pics.service';
import { PinfanPics } from '../../../../../../main/webapp/app/entities/pinfan-pics/pinfan-pics.model';
import { PinFanActivityService } from '../../../../../../main/webapp/app/entities/pin-fan-activity';

describe('Component Tests', () => {

    describe('PinfanPics Management Dialog Component', () => {
        let comp: PinfanPicsDialogComponent;
        let fixture: ComponentFixture<PinfanPicsDialogComponent>;
        let service: PinfanPicsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [PinfanPicsDialogComponent],
                providers: [
                    PinFanActivityService,
                    PinfanPicsService
                ]
            })
            .overrideTemplate(PinfanPicsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PinfanPicsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PinfanPicsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PinfanPics(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.pinfanPics = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'pinfanPicsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PinfanPics();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.pinfanPics = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'pinfanPicsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
