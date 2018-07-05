/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { AitpapiTestModule } from '../../../test.module';
import { CommentPicDetailComponent } from '../../../../../../main/webapp/app/entities/comment-pic/comment-pic-detail.component';
import { CommentPicService } from '../../../../../../main/webapp/app/entities/comment-pic/comment-pic.service';
import { CommentPic } from '../../../../../../main/webapp/app/entities/comment-pic/comment-pic.model';

describe('Component Tests', () => {

    describe('CommentPic Management Detail Component', () => {
        let comp: CommentPicDetailComponent;
        let fixture: ComponentFixture<CommentPicDetailComponent>;
        let service: CommentPicService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [CommentPicDetailComponent],
                providers: [
                    CommentPicService
                ]
            })
            .overrideTemplate(CommentPicDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommentPicDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentPicService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CommentPic(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.commentPic).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
