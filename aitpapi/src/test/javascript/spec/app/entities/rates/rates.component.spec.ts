/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AitpapiTestModule } from '../../../test.module';
import { RatesComponent } from '../../../../../../main/webapp/app/entities/rates/rates.component';
import { RatesService } from '../../../../../../main/webapp/app/entities/rates/rates.service';
import { Rates } from '../../../../../../main/webapp/app/entities/rates/rates.model';

describe('Component Tests', () => {

    describe('Rates Management Component', () => {
        let comp: RatesComponent;
        let fixture: ComponentFixture<RatesComponent>;
        let service: RatesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [RatesComponent],
                providers: [
                    RatesService
                ]
            })
            .overrideTemplate(RatesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RatesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RatesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Rates(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.rates[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
