/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { AttendeeDetailComponent } from 'app/entities/attendee/attendee-detail.component';
import { Attendee } from 'app/shared/model/attendee.model';

describe('Component Tests', () => {
    describe('Attendee Management Detail Component', () => {
        let comp: AttendeeDetailComponent;
        let fixture: ComponentFixture<AttendeeDetailComponent>;
        const route = ({ data: of({ attendee: new Attendee(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [AttendeeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AttendeeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AttendeeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.attendee).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
