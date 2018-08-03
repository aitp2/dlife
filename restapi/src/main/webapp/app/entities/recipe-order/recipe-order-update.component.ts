import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IRecipeOrder } from 'app/shared/model/recipe-order.model';
import { RecipeOrderService } from './recipe-order.service';
import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe';

@Component({
    selector: 'jhi-recipe-order-update',
    templateUrl: './recipe-order-update.component.html'
})
export class RecipeOrderUpdateComponent implements OnInit {
    private _recipeOrder: IRecipeOrder;
    isSaving: boolean;

    recipes: IRecipe[];
    createTime: string;
    modifyTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private recipeOrderService: RecipeOrderService,
        private recipeService: RecipeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recipeOrder }) => {
            this.recipeOrder = recipeOrder;
        });
        this.recipeService.query().subscribe(
            (res: HttpResponse<IRecipe[]>) => {
                this.recipes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.recipeOrder.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        this.recipeOrder.modifyTime = moment(this.modifyTime, DATE_TIME_FORMAT);
        if (this.recipeOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.recipeOrderService.update(this.recipeOrder));
        } else {
            this.subscribeToSaveResponse(this.recipeOrderService.create(this.recipeOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRecipeOrder>>) {
        result.subscribe((res: HttpResponse<IRecipeOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRecipeById(index: number, item: IRecipe) {
        return item.id;
    }
    get recipeOrder() {
        return this._recipeOrder;
    }

    set recipeOrder(recipeOrder: IRecipeOrder) {
        this._recipeOrder = recipeOrder;
        this.createTime = moment(recipeOrder.createTime).format(DATE_TIME_FORMAT);
        this.modifyTime = moment(recipeOrder.modifyTime).format(DATE_TIME_FORMAT);
    }
}
