/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { ClockinSummaryDetailComponent } from 'app/entities/clockin-summary/clockin-summary-detail.component';
import { ClockinSummary } from 'app/shared/model/clockin-summary.model';

describe('Component Tests', () => {
    describe('ClockinSummary Management Detail Component', () => {
        let comp: ClockinSummaryDetailComponent;
        let fixture: ComponentFixture<ClockinSummaryDetailComponent>;
        const route = ({ data: of({ clockinSummary: new ClockinSummary(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ClockinSummaryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ClockinSummaryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClockinSummaryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.clockinSummary).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
