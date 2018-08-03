/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { CommentPicUpdateComponent } from 'app/entities/comment-pic/comment-pic-update.component';
import { CommentPicService } from 'app/entities/comment-pic/comment-pic.service';
import { CommentPic } from 'app/shared/model/comment-pic.model';

describe('Component Tests', () => {
    describe('CommentPic Management Update Component', () => {
        let comp: CommentPicUpdateComponent;
        let fixture: ComponentFixture<CommentPicUpdateComponent>;
        let service: CommentPicService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [CommentPicUpdateComponent]
            })
                .overrideTemplate(CommentPicUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommentPicUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentPicService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CommentPic(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.commentPic = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CommentPic();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.commentPic = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
