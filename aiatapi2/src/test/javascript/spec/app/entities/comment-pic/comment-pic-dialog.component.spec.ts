/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { CommentPicDialogComponent } from '../../../../../../main/webapp/app/entities/comment-pic/comment-pic-dialog.component';
import { CommentPicService } from '../../../../../../main/webapp/app/entities/comment-pic/comment-pic.service';
import { CommentPic } from '../../../../../../main/webapp/app/entities/comment-pic/comment-pic.model';
import { CommentService } from '../../../../../../main/webapp/app/entities/comment';

describe('Component Tests', () => {

    describe('CommentPic Management Dialog Component', () => {
        let comp: CommentPicDialogComponent;
        let fixture: ComponentFixture<CommentPicDialogComponent>;
        let service: CommentPicService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [CommentPicDialogComponent],
                providers: [
                    CommentService,
                    CommentPicService
                ]
            })
            .overrideTemplate(CommentPicDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommentPicDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentPicService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CommentPic(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.commentPic = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'commentPicListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CommentPic();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.commentPic = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'commentPicListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
