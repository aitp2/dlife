/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { PinFanActivityDetailComponent } from 'app/entities/pin-fan-activity/pin-fan-activity-detail.component';
import { PinFanActivity } from 'app/shared/model/pin-fan-activity.model';

describe('Component Tests', () => {
    describe('PinFanActivity Management Detail Component', () => {
        let comp: PinFanActivityDetailComponent;
        let fixture: ComponentFixture<PinFanActivityDetailComponent>;
        const route = ({ data: of({ pinFanActivity: new PinFanActivity(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [PinFanActivityDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PinFanActivityDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PinFanActivityDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pinFanActivity).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
