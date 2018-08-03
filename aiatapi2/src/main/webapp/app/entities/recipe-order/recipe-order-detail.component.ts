import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecipeOrder } from 'app/shared/model/recipe-order.model';

@Component({
    selector: 'jhi-recipe-order-detail',
    templateUrl: './recipe-order-detail.component.html'
})
export class RecipeOrderDetailComponent implements OnInit {
    recipeOrder: IRecipeOrder;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recipeOrder }) => {
            this.recipeOrder = recipeOrder;
        });
    }

    previousState() {
        window.history.back();
    }
}
