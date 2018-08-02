/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { CommentPicDetailComponent } from 'app/entities/comment-pic/comment-pic-detail.component';
import { CommentPic } from 'app/shared/model/comment-pic.model';

describe('Component Tests', () => {
    describe('CommentPic Management Detail Component', () => {
        let comp: CommentPicDetailComponent;
        let fixture: ComponentFixture<CommentPicDetailComponent>;
        const route = ({ data: of({ commentPic: new CommentPic(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [CommentPicDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommentPicDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommentPicDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commentPic).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
