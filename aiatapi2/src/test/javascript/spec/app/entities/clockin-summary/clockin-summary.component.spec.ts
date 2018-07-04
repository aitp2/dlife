/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { AitpapiTestModule } from '../../../test.module';
import { ClockinSummaryComponent } from '../../../../../../main/webapp/app/entities/clockin-summary/clockin-summary.component';
import { ClockinSummaryService } from '../../../../../../main/webapp/app/entities/clockin-summary/clockin-summary.service';
import { ClockinSummary } from '../../../../../../main/webapp/app/entities/clockin-summary/clockin-summary.model';

describe('Component Tests', () => {

    describe('ClockinSummary Management Component', () => {
        let comp: ClockinSummaryComponent;
        let fixture: ComponentFixture<ClockinSummaryComponent>;
        let service: ClockinSummaryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ClockinSummaryComponent],
                providers: [
                    ClockinSummaryService
                ]
            })
            .overrideTemplate(ClockinSummaryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClockinSummaryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClockinSummaryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new ClockinSummary(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.clockinSummaries[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
