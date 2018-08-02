import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from './recipe.service';

@Component({
    selector: 'jhi-recipe-update',
    templateUrl: './recipe-update.component.html'
})
export class RecipeUpdateComponent implements OnInit {
    private _recipe: IRecipe;
    isSaving: boolean;
    startTime: string;
    endTime: string;
    createTime: string;
    modifyTime: string;

    constructor(private recipeService: RecipeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recipe }) => {
            this.recipe = recipe;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.recipe.startTime = moment(this.startTime, DATE_TIME_FORMAT);
        this.recipe.endTime = moment(this.endTime, DATE_TIME_FORMAT);
        this.recipe.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        this.recipe.modifyTime = moment(this.modifyTime, DATE_TIME_FORMAT);
        if (this.recipe.id !== undefined) {
            this.subscribeToSaveResponse(this.recipeService.update(this.recipe));
        } else {
            this.subscribeToSaveResponse(this.recipeService.create(this.recipe));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRecipe>>) {
        result.subscribe((res: HttpResponse<IRecipe>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get recipe() {
        return this._recipe;
    }

    set recipe(recipe: IRecipe) {
        this._recipe = recipe;
        this.startTime = moment(recipe.startTime).format(DATE_TIME_FORMAT);
        this.endTime = moment(recipe.endTime).format(DATE_TIME_FORMAT);
        this.createTime = moment(recipe.createTime).format(DATE_TIME_FORMAT);
        this.modifyTime = moment(recipe.modifyTime).format(DATE_TIME_FORMAT);
    }
}
