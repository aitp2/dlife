/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { AitpapiTestModule } from '../../../test.module';
import { AttendeeDetailComponent } from '../../../../../../main/webapp/app/entities/attendee/attendee-detail.component';
import { AttendeeService } from '../../../../../../main/webapp/app/entities/attendee/attendee.service';
import { Attendee } from '../../../../../../main/webapp/app/entities/attendee/attendee.model';

describe('Component Tests', () => {

    describe('Attendee Management Detail Component', () => {
        let comp: AttendeeDetailComponent;
        let fixture: ComponentFixture<AttendeeDetailComponent>;
        let service: AttendeeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [AttendeeDetailComponent],
                providers: [
                    AttendeeService
                ]
            })
            .overrideTemplate(AttendeeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AttendeeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AttendeeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Attendee(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.attendee).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
