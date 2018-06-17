import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Image } from './image.model';
import { ImagePopupService } from './image-popup.service';
import { ImageService } from './image.service';
import { Recipe, RecipeService } from '../recipe';
import { Evaluate, EvaluateService } from '../evaluate';

@Component({
    selector: 'jhi-image-dialog',
    templateUrl: './image-dialog.component.html'
})
export class ImageDialogComponent implements OnInit {

    image: Image;
    isSaving: boolean;

    recipes: Recipe[];

    evaluates: Evaluate[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private imageService: ImageService,
        private recipeService: RecipeService,
        private evaluateService: EvaluateService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.recipeService.query()
            .subscribe((res: HttpResponse<Recipe[]>) => { this.recipes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.evaluateService.query()
            .subscribe((res: HttpResponse<Evaluate[]>) => { this.evaluates = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.image.id !== undefined) {
            this.subscribeToSaveResponse(
                this.imageService.update(this.image));
        } else {
            this.subscribeToSaveResponse(
                this.imageService.create(this.image));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Image>>) {
        result.subscribe((res: HttpResponse<Image>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Image) {
        this.eventManager.broadcast({ name: 'imageListModification', content: 'OK'});
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

    trackEvaluateById(index: number, item: Evaluate) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-image-popup',
    template: ''
})
export class ImagePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private imagePopupService: ImagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.imagePopupService
                    .open(ImageDialogComponent as Component, params['id']);
            } else {
                this.imagePopupService
                    .open(ImageDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
