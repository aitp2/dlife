/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { UserPointDetailsUpdateComponent } from 'app/entities/user-point-details/user-point-details-update.component';
import { UserPointDetailsService } from 'app/entities/user-point-details/user-point-details.service';
import { UserPointDetails } from 'app/shared/model/user-point-details.model';

describe('Component Tests', () => {
    describe('UserPointDetails Management Update Component', () => {
        let comp: UserPointDetailsUpdateComponent;
        let fixture: ComponentFixture<UserPointDetailsUpdateComponent>;
        let service: UserPointDetailsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [UserPointDetailsUpdateComponent]
            })
                .overrideTemplate(UserPointDetailsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserPointDetailsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserPointDetailsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new UserPointDetails(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.userPointDetails = entity;
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
                    const entity = new UserPointDetails();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.userPointDetails = entity;
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
