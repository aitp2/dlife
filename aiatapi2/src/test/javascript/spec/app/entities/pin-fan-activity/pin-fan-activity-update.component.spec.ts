/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { PinFanActivityUpdateComponent } from 'app/entities/pin-fan-activity/pin-fan-activity-update.component';
import { PinFanActivityService } from 'app/entities/pin-fan-activity/pin-fan-activity.service';
import { PinFanActivity } from 'app/shared/model/pin-fan-activity.model';

describe('Component Tests', () => {
    describe('PinFanActivity Management Update Component', () => {
        let comp: PinFanActivityUpdateComponent;
        let fixture: ComponentFixture<PinFanActivityUpdateComponent>;
        let service: PinFanActivityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [PinFanActivityUpdateComponent]
            })
                .overrideTemplate(PinFanActivityUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PinFanActivityUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PinFanActivityService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PinFanActivity(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pinFanActivity = entity;
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
                    const entity = new PinFanActivity();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pinFanActivity = entity;
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
