/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { AttendeeUpdateComponent } from 'app/entities/attendee/attendee-update.component';
import { AttendeeService } from 'app/entities/attendee/attendee.service';
import { Attendee } from 'app/shared/model/attendee.model';

describe('Component Tests', () => {
    describe('Attendee Management Update Component', () => {
        let comp: AttendeeUpdateComponent;
        let fixture: ComponentFixture<AttendeeUpdateComponent>;
        let service: AttendeeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [AttendeeUpdateComponent]
            })
                .overrideTemplate(AttendeeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AttendeeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AttendeeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Attendee(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.attendee = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Attendee();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.attendee = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
