/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { RecipeDetailComponent } from 'app/entities/recipe/recipe-detail.component';
import { Recipe } from 'app/shared/model/recipe.model';

describe('Component Tests', () => {
    describe('Recipe Management Detail Component', () => {
        let comp: RecipeDetailComponent;
        let fixture: ComponentFixture<RecipeDetailComponent>;
        const route = ({ data: of({ recipe: new Recipe(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [RecipeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RecipeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecipeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.recipe).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
