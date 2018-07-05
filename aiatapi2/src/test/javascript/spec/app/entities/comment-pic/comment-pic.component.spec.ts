/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { AitpapiTestModule } from '../../../test.module';
import { CommentPicComponent } from '../../../../../../main/webapp/app/entities/comment-pic/comment-pic.component';
import { CommentPicService } from '../../../../../../main/webapp/app/entities/comment-pic/comment-pic.service';
import { CommentPic } from '../../../../../../main/webapp/app/entities/comment-pic/comment-pic.model';

describe('Component Tests', () => {

    describe('CommentPic Management Component', () => {
        let comp: CommentPicComponent;
        let fixture: ComponentFixture<CommentPicComponent>;
        let service: CommentPicService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [CommentPicComponent],
                providers: [
                    CommentPicService
                ]
            })
            .overrideTemplate(CommentPicComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommentPicComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentPicService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CommentPic(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.commentPics[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
