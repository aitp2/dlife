import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RecipeOrder } from './recipe-order.model';
import { RecipeOrderPopupService } from './recipe-order-popup.service';
import { RecipeOrderService } from './recipe-order.service';

@Component({
    selector: 'jhi-recipe-order-delete-dialog',
    templateUrl: './recipe-order-delete-dialog.component.html'
})
export class RecipeOrderDeleteDialogComponent {

    recipeOrder: RecipeOrder;

    constructor(
        private recipeOrderService: RecipeOrderService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.recipeOrderService.delete(id).subscribe((response) => {
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

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private recipeOrderPopupService: RecipeOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.recipeOrderPopupService
                .open(RecipeOrderDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
