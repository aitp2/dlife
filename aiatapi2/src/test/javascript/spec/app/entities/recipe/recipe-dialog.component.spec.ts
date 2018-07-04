/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { RecipeDialogComponent } from '../../../../../../main/webapp/app/entities/recipe/recipe-dialog.component';
import { RecipeService } from '../../../../../../main/webapp/app/entities/recipe/recipe.service';
import { Recipe } from '../../../../../../main/webapp/app/entities/recipe/recipe.model';

describe('Component Tests', () => {

    describe('Recipe Management Dialog Component', () => {
        let comp: RecipeDialogComponent;
        let fixture: ComponentFixture<RecipeDialogComponent>;
        let service: RecipeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [RecipeDialogComponent],
                providers: [
                    RecipeService
                ]
            })
            .overrideTemplate(RecipeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RecipeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecipeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Recipe(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.recipe = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'recipeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Recipe();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.recipe = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'recipeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
