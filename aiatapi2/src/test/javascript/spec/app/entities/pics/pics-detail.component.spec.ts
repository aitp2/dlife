/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { AitpapiTestModule } from '../../../test.module';
import { PicsDetailComponent } from '../../../../../../main/webapp/app/entities/pics/pics-detail.component';
import { PicsService } from '../../../../../../main/webapp/app/entities/pics/pics.service';
import { Pics } from '../../../../../../main/webapp/app/entities/pics/pics.model';

describe('Component Tests', () => {

    describe('Pics Management Detail Component', () => {
        let comp: PicsDetailComponent;
        let fixture: ComponentFixture<PicsDetailComponent>;
        let service: PicsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [PicsDetailComponent],
                providers: [
                    PicsService
                ]
            })
            .overrideTemplate(PicsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PicsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PicsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Pics(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.pics).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
