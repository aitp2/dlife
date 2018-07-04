/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { AitpapiTestModule } from '../../../test.module';
import { PinfanPicsComponent } from '../../../../../../main/webapp/app/entities/pinfan-pics/pinfan-pics.component';
import { PinfanPicsService } from '../../../../../../main/webapp/app/entities/pinfan-pics/pinfan-pics.service';
import { PinfanPics } from '../../../../../../main/webapp/app/entities/pinfan-pics/pinfan-pics.model';

describe('Component Tests', () => {

    describe('PinfanPics Management Component', () => {
        let comp: PinfanPicsComponent;
        let fixture: ComponentFixture<PinfanPicsComponent>;
        let service: PinfanPicsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [PinfanPicsComponent],
                providers: [
                    PinfanPicsService
                ]
            })
            .overrideTemplate(PinfanPicsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PinfanPicsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PinfanPicsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new PinfanPics(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.pinfanPics[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
