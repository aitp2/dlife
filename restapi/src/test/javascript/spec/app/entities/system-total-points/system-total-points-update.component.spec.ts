/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { SystemTotalPointsUpdateComponent } from 'app/entities/system-total-points/system-total-points-update.component';
import { SystemTotalPointsService } from 'app/entities/system-total-points/system-total-points.service';
import { SystemTotalPoints } from 'app/shared/model/system-total-points.model';

describe('Component Tests', () => {
    describe('SystemTotalPoints Management Update Component', () => {
        let comp: SystemTotalPointsUpdateComponent;
        let fixture: ComponentFixture<SystemTotalPointsUpdateComponent>;
        let service: SystemTotalPointsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [SystemTotalPointsUpdateComponent]
            })
                .overrideTemplate(SystemTotalPointsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SystemTotalPointsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SystemTotalPointsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SystemTotalPoints(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.systemTotalPoints = entity;
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
                    const entity = new SystemTotalPoints();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.systemTotalPoints = entity;
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
