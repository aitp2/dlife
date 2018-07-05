/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { AitpapiTestModule } from '../../../test.module';
import { ClockInDetailComponent } from '../../../../../../main/webapp/app/entities/clock-in/clock-in-detail.component';
import { ClockInService } from '../../../../../../main/webapp/app/entities/clock-in/clock-in.service';
import { ClockIn } from '../../../../../../main/webapp/app/entities/clock-in/clock-in.model';

describe('Component Tests', () => {

    describe('ClockIn Management Detail Component', () => {
        let comp: ClockInDetailComponent;
        let fixture: ComponentFixture<ClockInDetailComponent>;
        let service: ClockInService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ClockInDetailComponent],
                providers: [
                    ClockInService
                ]
            })
            .overrideTemplate(ClockInDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClockInDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClockInService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new ClockIn(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.clockIn).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
