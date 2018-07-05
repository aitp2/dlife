/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { AitpapiTestModule } from '../../../test.module';
import { ClockinSummaryDetailComponent } from '../../../../../../main/webapp/app/entities/clockin-summary/clockin-summary-detail.component';
import { ClockinSummaryService } from '../../../../../../main/webapp/app/entities/clockin-summary/clockin-summary.service';
import { ClockinSummary } from '../../../../../../main/webapp/app/entities/clockin-summary/clockin-summary.model';

describe('Component Tests', () => {

    describe('ClockinSummary Management Detail Component', () => {
        let comp: ClockinSummaryDetailComponent;
        let fixture: ComponentFixture<ClockinSummaryDetailComponent>;
        let service: ClockinSummaryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ClockinSummaryDetailComponent],
                providers: [
                    ClockinSummaryService
                ]
            })
            .overrideTemplate(ClockinSummaryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClockinSummaryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClockinSummaryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new ClockinSummary(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.clockinSummary).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
