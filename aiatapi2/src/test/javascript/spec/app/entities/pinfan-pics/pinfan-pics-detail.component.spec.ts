/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { AitpapiTestModule } from '../../../test.module';
import { PinfanPicsDetailComponent } from '../../../../../../main/webapp/app/entities/pinfan-pics/pinfan-pics-detail.component';
import { PinfanPicsService } from '../../../../../../main/webapp/app/entities/pinfan-pics/pinfan-pics.service';
import { PinfanPics } from '../../../../../../main/webapp/app/entities/pinfan-pics/pinfan-pics.model';

describe('Component Tests', () => {

    describe('PinfanPics Management Detail Component', () => {
        let comp: PinfanPicsDetailComponent;
        let fixture: ComponentFixture<PinfanPicsDetailComponent>;
        let service: PinfanPicsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [PinfanPicsDetailComponent],
                providers: [
                    PinfanPicsService
                ]
            })
            .overrideTemplate(PinfanPicsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PinfanPicsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PinfanPicsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new PinfanPics(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.pinfanPics).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
