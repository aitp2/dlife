/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { SystemTotalPointsDetailComponent } from 'app/entities/system-total-points/system-total-points-detail.component';
import { SystemTotalPoints } from 'app/shared/model/system-total-points.model';

describe('Component Tests', () => {
    describe('SystemTotalPoints Management Detail Component', () => {
        let comp: SystemTotalPointsDetailComponent;
        let fixture: ComponentFixture<SystemTotalPointsDetailComponent>;
        const route = ({ data: of({ systemTotalPoints: new SystemTotalPoints(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [SystemTotalPointsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SystemTotalPointsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SystemTotalPointsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.systemTotalPoints).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
