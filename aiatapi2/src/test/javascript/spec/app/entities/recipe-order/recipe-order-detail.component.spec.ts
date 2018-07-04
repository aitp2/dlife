/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { AitpapiTestModule } from '../../../test.module';
import { RecipeOrderDetailComponent } from '../../../../../../main/webapp/app/entities/recipe-order/recipe-order-detail.component';
import { RecipeOrderService } from '../../../../../../main/webapp/app/entities/recipe-order/recipe-order.service';
import { RecipeOrder } from '../../../../../../main/webapp/app/entities/recipe-order/recipe-order.model';

describe('Component Tests', () => {

    describe('RecipeOrder Management Detail Component', () => {
        let comp: RecipeOrderDetailComponent;
        let fixture: ComponentFixture<RecipeOrderDetailComponent>;
        let service: RecipeOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [RecipeOrderDetailComponent],
                providers: [
                    RecipeOrderService
                ]
            })
            .overrideTemplate(RecipeOrderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RecipeOrderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecipeOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new RecipeOrder(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.recipeOrder).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
