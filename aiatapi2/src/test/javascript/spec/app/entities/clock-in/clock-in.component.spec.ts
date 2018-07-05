/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { AitpapiTestModule } from '../../../test.module';
import { ClockInComponent } from '../../../../../../main/webapp/app/entities/clock-in/clock-in.component';
import { ClockInService } from '../../../../../../main/webapp/app/entities/clock-in/clock-in.service';
import { ClockIn } from '../../../../../../main/webapp/app/entities/clock-in/clock-in.model';

describe('Component Tests', () => {

    describe('ClockIn Management Component', () => {
        let comp: ClockInComponent;
        let fixture: ComponentFixture<ClockInComponent>;
        let service: ClockInService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ClockInComponent],
                providers: [
                    ClockInService
                ]
            })
            .overrideTemplate(ClockInComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClockInComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClockInService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new ClockIn(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.clockIns[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
