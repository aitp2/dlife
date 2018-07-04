/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { AitpapiTestModule } from '../../../test.module';
import { PicsComponent } from '../../../../../../main/webapp/app/entities/pics/pics.component';
import { PicsService } from '../../../../../../main/webapp/app/entities/pics/pics.service';
import { Pics } from '../../../../../../main/webapp/app/entities/pics/pics.model';

describe('Component Tests', () => {

    describe('Pics Management Component', () => {
        let comp: PicsComponent;
        let fixture: ComponentFixture<PicsComponent>;
        let service: PicsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [PicsComponent],
                providers: [
                    PicsService
                ]
            })
            .overrideTemplate(PicsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PicsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PicsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Pics(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.pics[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
