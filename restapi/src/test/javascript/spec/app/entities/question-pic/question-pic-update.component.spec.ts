/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { QuestionPicUpdateComponent } from 'app/entities/question-pic/question-pic-update.component';
import { QuestionPicService } from 'app/entities/question-pic/question-pic.service';
import { QuestionPic } from 'app/shared/model/question-pic.model';

describe('Component Tests', () => {
    describe('QuestionPic Management Update Component', () => {
        let comp: QuestionPicUpdateComponent;
        let fixture: ComponentFixture<QuestionPicUpdateComponent>;
        let service: QuestionPicService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [QuestionPicUpdateComponent]
            })
                .overrideTemplate(QuestionPicUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuestionPicUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionPicService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new QuestionPic(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.questionPic = entity;
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
                    const entity = new QuestionPic();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.questionPic = entity;
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
