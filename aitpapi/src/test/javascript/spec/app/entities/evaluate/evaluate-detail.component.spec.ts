/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AitpapiTestModule } from '../../../test.module';
import { EvaluateDetailComponent } from '../../../../../../main/webapp/app/entities/evaluate/evaluate-detail.component';
import { EvaluateService } from '../../../../../../main/webapp/app/entities/evaluate/evaluate.service';
import { Evaluate } from '../../../../../../main/webapp/app/entities/evaluate/evaluate.model';

describe('Component Tests', () => {

    describe('Evaluate Management Detail Component', () => {
        let comp: EvaluateDetailComponent;
        let fixture: ComponentFixture<EvaluateDetailComponent>;
        let service: EvaluateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [EvaluateDetailComponent],
                providers: [
                    EvaluateService
                ]
            })
            .overrideTemplate(EvaluateDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EvaluateDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EvaluateService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Evaluate(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.evaluate).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
