/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { ThumbsUpUpdateComponent } from 'app/entities/thumbs-up/thumbs-up-update.component';
import { ThumbsUpService } from 'app/entities/thumbs-up/thumbs-up.service';
import { ThumbsUp } from 'app/shared/model/thumbs-up.model';

describe('Component Tests', () => {
    describe('ThumbsUp Management Update Component', () => {
        let comp: ThumbsUpUpdateComponent;
        let fixture: ComponentFixture<ThumbsUpUpdateComponent>;
        let service: ThumbsUpService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ThumbsUpUpdateComponent]
            })
                .overrideTemplate(ThumbsUpUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ThumbsUpUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThumbsUpService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ThumbsUp(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.thumbsUp = entity;
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
                    const entity = new ThumbsUp();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.thumbsUp = entity;
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
