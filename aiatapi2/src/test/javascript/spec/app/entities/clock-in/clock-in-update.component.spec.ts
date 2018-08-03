/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { ClockInUpdateComponent } from 'app/entities/clock-in/clock-in-update.component';
import { ClockInService } from 'app/entities/clock-in/clock-in.service';
import { ClockIn } from 'app/shared/model/clock-in.model';

describe('Component Tests', () => {
    describe('ClockIn Management Update Component', () => {
        let comp: ClockInUpdateComponent;
        let fixture: ComponentFixture<ClockInUpdateComponent>;
        let service: ClockInService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ClockInUpdateComponent]
            })
                .overrideTemplate(ClockInUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClockInUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClockInService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ClockIn(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.clockIn = entity;
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
                    const entity = new ClockIn();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.clockIn = entity;
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
