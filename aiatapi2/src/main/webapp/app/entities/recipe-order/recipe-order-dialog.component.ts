import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RecipeOrder } from './recipe-order.model';
import { RecipeOrderPopupService } from './recipe-order-popup.service';
import { RecipeOrderService } from './recipe-order.service';
import { Recipe, RecipeService } from '../recipe';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-recipe-order-dialog',
    templateUrl: './recipe-order-dialog.component.html'
})
export class RecipeOrderDialogComponent implements OnInit {

    recipeOrder: RecipeOrder;
    isSaving: boolean;

    recipes: Recipe[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private recipeOrderService: RecipeOrderService,
        private recipeService: RecipeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.recipeService.query()
            .subscribe((res: ResponseWrapper) => { this.recipes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.recipeOrder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.recipeOrderService.update(this.recipeOrder));
        } else {
            this.subscribeToSaveResponse(
                this.recipeOrderService.create(this.recipeOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<RecipeOrder>) {
        result.subscribe((res: RecipeOrder) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: RecipeOrder) {
        this.eventManager.broadcast({ name: 'recipeOrderListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRecipeById(index: number, item: Recipe) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-recipe-order-popup',
    template: ''
})
export class RecipeOrderPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private recipeOrderPopupService: RecipeOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.recipeOrderPopupService
                    .open(RecipeOrderDialogComponent as Component, params['id']);
            } else {
                this.recipeOrderPopupService
                    .open(RecipeOrderDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
