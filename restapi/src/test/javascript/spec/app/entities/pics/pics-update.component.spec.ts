/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { PicsUpdateComponent } from 'app/entities/pics/pics-update.component';
import { PicsService } from 'app/entities/pics/pics.service';
import { Pics } from 'app/shared/model/pics.model';

describe('Component Tests', () => {
    describe('Pics Management Update Component', () => {
        let comp: PicsUpdateComponent;
        let fixture: ComponentFixture<PicsUpdateComponent>;
        let service: PicsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [PicsUpdateComponent]
            })
                .overrideTemplate(PicsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PicsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PicsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Pics(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pics = entity;
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
                    const entity = new Pics();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pics = entity;
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
