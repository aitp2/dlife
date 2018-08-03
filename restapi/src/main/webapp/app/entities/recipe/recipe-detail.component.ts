import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecipe } from 'app/shared/model/recipe.model';

@Component({
    selector: 'jhi-recipe-detail',
    templateUrl: './recipe-detail.component.html'
})
export class RecipeDetailComponent implements OnInit {
    recipe: IRecipe;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recipe }) => {
            this.recipe = recipe;
        });
    }

    previousState() {
        window.history.back();
    }
}
