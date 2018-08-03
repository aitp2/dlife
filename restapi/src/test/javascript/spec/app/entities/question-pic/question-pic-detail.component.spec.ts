/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { QuestionPicDetailComponent } from 'app/entities/question-pic/question-pic-detail.component';
import { QuestionPic } from 'app/shared/model/question-pic.model';

describe('Component Tests', () => {
    describe('QuestionPic Management Detail Component', () => {
        let comp: QuestionPicDetailComponent;
        let fixture: ComponentFixture<QuestionPicDetailComponent>;
        const route = ({ data: of({ questionPic: new QuestionPic(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [QuestionPicDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(QuestionPicDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuestionPicDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.questionPic).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
