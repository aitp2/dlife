/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { ImageDialogComponent } from '../../../../../../main/webapp/app/entities/image/image-dialog.component';
import { ImageService } from '../../../../../../main/webapp/app/entities/image/image.service';
import { Image } from '../../../../../../main/webapp/app/entities/image/image.model';
import { RecipeService } from '../../../../../../main/webapp/app/entities/recipe';

describe('Component Tests', () => {

    describe('Image Management Dialog Component', () => {
        let comp: ImageDialogComponent;
        let fixture: ComponentFixture<ImageDialogComponent>;
        let service: ImageService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ImageDialogComponent],
                providers: [
                    RecipeService,
                    ImageService
                ]
            })
            .overrideTemplate(ImageDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ImageDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImageService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Image(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.image = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'imageListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Image();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.image = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'imageListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
