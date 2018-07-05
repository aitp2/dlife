/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { AitpapiTestModule } from '../../../test.module';
import { PinFanActivityComponent } from '../../../../../../main/webapp/app/entities/pin-fan-activity/pin-fan-activity.component';
import { PinFanActivityService } from '../../../../../../main/webapp/app/entities/pin-fan-activity/pin-fan-activity.service';
import { PinFanActivity } from '../../../../../../main/webapp/app/entities/pin-fan-activity/pin-fan-activity.model';

describe('Component Tests', () => {

    describe('PinFanActivity Management Component', () => {
        let comp: PinFanActivityComponent;
        let fixture: ComponentFixture<PinFanActivityComponent>;
        let service: PinFanActivityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [PinFanActivityComponent],
                providers: [
                    PinFanActivityService
                ]
            })
            .overrideTemplate(PinFanActivityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PinFanActivityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PinFanActivityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new PinFanActivity(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.pinFanActivities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
