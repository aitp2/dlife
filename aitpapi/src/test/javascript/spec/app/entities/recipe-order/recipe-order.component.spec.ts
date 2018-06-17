/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AitpapiTestModule } from '../../../test.module';
import { RecipeOrderComponent } from '../../../../../../main/webapp/app/entities/recipe-order/recipe-order.component';
import { RecipeOrderService } from '../../../../../../main/webapp/app/entities/recipe-order/recipe-order.service';
import { RecipeOrder } from '../../../../../../main/webapp/app/entities/recipe-order/recipe-order.model';

describe('Component Tests', () => {

    describe('RecipeOrder Management Component', () => {
        let comp: RecipeOrderComponent;
        let fixture: ComponentFixture<RecipeOrderComponent>;
        let service: RecipeOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [RecipeOrderComponent],
                providers: [
                    RecipeOrderService
                ]
            })
            .overrideTemplate(RecipeOrderComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RecipeOrderComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecipeOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new RecipeOrder(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.recipeOrders[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
