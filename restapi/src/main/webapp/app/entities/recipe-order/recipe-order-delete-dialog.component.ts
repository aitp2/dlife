import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecipeOrder } from 'app/shared/model/recipe-order.model';
import { RecipeOrderService } from './recipe-order.service';

@Component({
    selector: 'jhi-recipe-order-delete-dialog',
    templateUrl: './recipe-order-delete-dialog.component.html'
})
export class RecipeOrderDeleteDialogComponent {
    recipeOrder: IRecipeOrder;

    constructor(
        private recipeOrderService: RecipeOrderService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.recipeOrderService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'recipeOrderListModification',
                content: 'Deleted an recipeOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-recipe-order-delete-popup',
    template: ''
})
export class RecipeOrderDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recipeOrder }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RecipeOrderDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.recipeOrder = recipeOrder;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
