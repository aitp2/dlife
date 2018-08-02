/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { ClockinSummaryUpdateComponent } from 'app/entities/clockin-summary/clockin-summary-update.component';
import { ClockinSummaryService } from 'app/entities/clockin-summary/clockin-summary.service';
import { ClockinSummary } from 'app/shared/model/clockin-summary.model';

describe('Component Tests', () => {
    describe('ClockinSummary Management Update Component', () => {
        let comp: ClockinSummaryUpdateComponent;
        let fixture: ComponentFixture<ClockinSummaryUpdateComponent>;
        let service: ClockinSummaryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ClockinSummaryUpdateComponent]
            })
                .overrideTemplate(ClockinSummaryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClockinSummaryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClockinSummaryService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ClockinSummary(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.clockinSummary = entity;
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
                    const entity = new ClockinSummary();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.clockinSummary = entity;
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
