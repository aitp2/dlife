/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { AitpapiTestModule } from '../../../test.module';
import { AttendeeComponent } from '../../../../../../main/webapp/app/entities/attendee/attendee.component';
import { AttendeeService } from '../../../../../../main/webapp/app/entities/attendee/attendee.service';
import { Attendee } from '../../../../../../main/webapp/app/entities/attendee/attendee.model';

describe('Component Tests', () => {

    describe('Attendee Management Component', () => {
        let comp: AttendeeComponent;
        let fixture: ComponentFixture<AttendeeComponent>;
        let service: AttendeeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [AttendeeComponent],
                providers: [
                    AttendeeService
                ]
            })
            .overrideTemplate(AttendeeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AttendeeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AttendeeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Attendee(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.attendees[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
