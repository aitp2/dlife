/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { AitpapiTestModule } from '../../../test.module';
import { PinFanActivityDetailComponent } from '../../../../../../main/webapp/app/entities/pin-fan-activity/pin-fan-activity-detail.component';
import { PinFanActivityService } from '../../../../../../main/webapp/app/entities/pin-fan-activity/pin-fan-activity.service';
import { PinFanActivity } from '../../../../../../main/webapp/app/entities/pin-fan-activity/pin-fan-activity.model';

describe('Component Tests', () => {

    describe('PinFanActivity Management Detail Component', () => {
        let comp: PinFanActivityDetailComponent;
        let fixture: ComponentFixture<PinFanActivityDetailComponent>;
        let service: PinFanActivityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [PinFanActivityDetailComponent],
                providers: [
                    PinFanActivityService
                ]
            })
            .overrideTemplate(PinFanActivityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PinFanActivityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PinFanActivityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new PinFanActivity(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.pinFanActivity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
