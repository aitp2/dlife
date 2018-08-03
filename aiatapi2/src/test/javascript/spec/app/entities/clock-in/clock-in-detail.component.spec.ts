/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { ClockInDetailComponent } from 'app/entities/clock-in/clock-in-detail.component';
import { ClockIn } from 'app/shared/model/clock-in.model';

describe('Component Tests', () => {
    describe('ClockIn Management Detail Component', () => {
        let comp: ClockInDetailComponent;
        let fixture: ComponentFixture<ClockInDetailComponent>;
        const route = ({ data: of({ clockIn: new ClockIn(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ClockInDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ClockInDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClockInDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.clockIn).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
