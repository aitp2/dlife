/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { RecipeOrderDeleteDialogComponent } from 'app/entities/recipe-order/recipe-order-delete-dialog.component';
import { RecipeOrderService } from 'app/entities/recipe-order/recipe-order.service';

describe('Component Tests', () => {
    describe('RecipeOrder Management Delete Component', () => {
        let comp: RecipeOrderDeleteDialogComponent;
        let fixture: ComponentFixture<RecipeOrderDeleteDialogComponent>;
        let service: RecipeOrderService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [RecipeOrderDeleteDialogComponent]
            })
                .overrideTemplate(RecipeOrderDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecipeOrderDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecipeOrderService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
