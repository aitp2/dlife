import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Evaluate } from './evaluate.model';
import { EvaluatePopupService } from './evaluate-popup.service';
import { EvaluateService } from './evaluate.service';
import { RecipeOrder, RecipeOrderService } from '../recipe-order';

@Component({
    selector: 'jhi-evaluate-dialog',
    templateUrl: './evaluate-dialog.component.html'
})
export class EvaluateDialogComponent implements OnInit {

    evaluate: Evaluate;
    isSaving: boolean;

    recipeorders: RecipeOrder[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private evaluateService: EvaluateService,
        private recipeOrderService: RecipeOrderService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.recipeOrderService.query()
            .subscribe((res: HttpResponse<RecipeOrder[]>) => { this.recipeorders = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.evaluate.id !== undefined) {
            this.subscribeToSaveResponse(
                this.evaluateService.update(this.evaluate));
        } else {
            this.subscribeToSaveResponse(
                this.evaluateService.create(this.evaluate));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Evaluate>>) {
        result.subscribe((res: HttpResponse<Evaluate>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Evaluate) {
        this.eventManager.broadcast({ name: 'evaluateListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRecipeOrderById(index: number, item: RecipeOrder) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-evaluate-popup',
    template: ''
})
export class EvaluatePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private evaluatePopupService: EvaluatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.evaluatePopupService
                    .open(EvaluateDialogComponent as Component, params['id']);
            } else {
                this.evaluatePopupService
                    .open(EvaluateDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
