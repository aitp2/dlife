/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AitpapiTestModule } from '../../../test.module';
import { RatesDetailComponent } from '../../../../../../main/webapp/app/entities/rates/rates-detail.component';
import { RatesService } from '../../../../../../main/webapp/app/entities/rates/rates.service';
import { Rates } from '../../../../../../main/webapp/app/entities/rates/rates.model';

describe('Component Tests', () => {

    describe('Rates Management Detail Component', () => {
        let comp: RatesDetailComponent;
        let fixture: ComponentFixture<RatesDetailComponent>;
        let service: RatesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [RatesDetailComponent],
                providers: [
                    RatesService
                ]
            })
            .overrideTemplate(RatesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RatesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RatesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Rates(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.rates).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
