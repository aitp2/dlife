/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { RecipeOrderDialogComponent } from '../../../../../../main/webapp/app/entities/recipe-order/recipe-order-dialog.component';
import { RecipeOrderService } from '../../../../../../main/webapp/app/entities/recipe-order/recipe-order.service';
import { RecipeOrder } from '../../../../../../main/webapp/app/entities/recipe-order/recipe-order.model';
import { RecipeService } from '../../../../../../main/webapp/app/entities/recipe';

describe('Component Tests', () => {

    describe('RecipeOrder Management Dialog Component', () => {
        let comp: RecipeOrderDialogComponent;
        let fixture: ComponentFixture<RecipeOrderDialogComponent>;
        let service: RecipeOrderService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [RecipeOrderDialogComponent],
                providers: [
                    RecipeService,
                    RecipeOrderService
                ]
            })
            .overrideTemplate(RecipeOrderDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RecipeOrderDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecipeOrderService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new RecipeOrder(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.recipeOrder = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'recipeOrderListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new RecipeOrder();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.recipeOrder = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'recipeOrderListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
