import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { RecipeOrder } from './recipe-order.model';
import { RecipeOrderService } from './recipe-order.service';

@Component({
    selector: 'jhi-recipe-order-detail',
    templateUrl: './recipe-order-detail.component.html'
})
export class RecipeOrderDetailComponent implements OnInit, OnDestroy {

    recipeOrder: RecipeOrder;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private recipeOrderService: RecipeOrderService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRecipeOrders();
    }

    load(id) {
        this.recipeOrderService.find(id).subscribe((recipeOrder) => {
            this.recipeOrder = recipeOrder;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRecipeOrders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'recipeOrderListModification',
            (response) => this.load(this.recipeOrder.id)
        );
    }
}
