/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { AitpapiTestModule } from '../../../test.module';
import { ImageComponent } from '../../../../../../main/webapp/app/entities/image/image.component';
import { ImageService } from '../../../../../../main/webapp/app/entities/image/image.service';
import { Image } from '../../../../../../main/webapp/app/entities/image/image.model';

describe('Component Tests', () => {

    describe('Image Management Component', () => {
        let comp: ImageComponent;
        let fixture: ComponentFixture<ImageComponent>;
        let service: ImageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ImageComponent],
                providers: [
                    ImageService
                ]
            })
            .overrideTemplate(ImageComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ImageComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Image(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.images[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
