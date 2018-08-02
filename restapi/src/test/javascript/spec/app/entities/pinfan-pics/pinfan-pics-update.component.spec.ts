/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { PinfanPicsUpdateComponent } from 'app/entities/pinfan-pics/pinfan-pics-update.component';
import { PinfanPicsService } from 'app/entities/pinfan-pics/pinfan-pics.service';
import { PinfanPics } from 'app/shared/model/pinfan-pics.model';

describe('Component Tests', () => {
    describe('PinfanPics Management Update Component', () => {
        let comp: PinfanPicsUpdateComponent;
        let fixture: ComponentFixture<PinfanPicsUpdateComponent>;
        let service: PinfanPicsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [PinfanPicsUpdateComponent]
            })
                .overrideTemplate(PinfanPicsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PinfanPicsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PinfanPicsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PinfanPics(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pinfanPics = entity;
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
                    const entity = new PinfanPics();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pinfanPics = entity;
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
