import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Recipe } from './recipe.model';
import { RecipeService } from './recipe.service';

@Injectable()
export class RecipePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private recipeService: RecipeService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.recipeService.find(id).subscribe((recipe) => {
                    recipe.startTime = this.datePipe
                        .transform(recipe.startTime, 'yyyy-MM-ddTHH:mm:ss');
                    recipe.endTime = this.datePipe
                        .transform(recipe.endTime, 'yyyy-MM-ddTHH:mm:ss');
                    recipe.createTime = this.datePipe
                        .transform(recipe.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    recipe.modifyTime = this.datePipe
                        .transform(recipe.modifyTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.recipeModalRef(component, recipe);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.recipeModalRef(component, new Recipe());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    recipeModalRef(component: Component, recipe: Recipe): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.recipe = recipe;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
