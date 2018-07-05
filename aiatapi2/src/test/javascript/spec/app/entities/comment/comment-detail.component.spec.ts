/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { AitpapiTestModule } from '../../../test.module';
import { CommentDetailComponent } from '../../../../../../main/webapp/app/entities/comment/comment-detail.component';
import { CommentService } from '../../../../../../main/webapp/app/entities/comment/comment.service';
import { Comment } from '../../../../../../main/webapp/app/entities/comment/comment.model';

describe('Component Tests', () => {

    describe('Comment Management Detail Component', () => {
        let comp: CommentDetailComponent;
        let fixture: ComponentFixture<CommentDetailComponent>;
        let service: CommentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [CommentDetailComponent],
                providers: [
                    CommentService
                ]
            })
            .overrideTemplate(CommentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Comment(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.comment).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
