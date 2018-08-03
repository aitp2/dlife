/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { RecipeOrderDetailComponent } from 'app/entities/recipe-order/recipe-order-detail.component';
import { RecipeOrder } from 'app/shared/model/recipe-order.model';

describe('Component Tests', () => {
    describe('RecipeOrder Management Detail Component', () => {
        let comp: RecipeOrderDetailComponent;
        let fixture: ComponentFixture<RecipeOrderDetailComponent>;
        const route = ({ data: of({ recipeOrder: new RecipeOrder(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [RecipeOrderDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RecipeOrderDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecipeOrderDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.recipeOrder).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
