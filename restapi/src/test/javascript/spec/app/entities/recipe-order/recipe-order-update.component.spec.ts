/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { RecipeOrderUpdateComponent } from 'app/entities/recipe-order/recipe-order-update.component';
import { RecipeOrderService } from 'app/entities/recipe-order/recipe-order.service';
import { RecipeOrder } from 'app/shared/model/recipe-order.model';

describe('Component Tests', () => {
    describe('RecipeOrder Management Update Component', () => {
        let comp: RecipeOrderUpdateComponent;
        let fixture: ComponentFixture<RecipeOrderUpdateComponent>;
        let service: RecipeOrderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [RecipeOrderUpdateComponent]
            })
                .overrideTemplate(RecipeOrderUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RecipeOrderUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecipeOrderService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RecipeOrder(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recipeOrder = entity;
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
                    const entity = new RecipeOrder();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recipeOrder = entity;
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
