import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RecipeOrder } from './recipe-order.model';
import { RecipeOrderService } from './recipe-order.service';

@Injectable()
export class RecipeOrderPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private recipeOrderService: RecipeOrderService

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
                this.recipeOrderService.find(id).subscribe((recipeOrder) => {
                    recipeOrder.createTime = this.datePipe
                        .transform(recipeOrder.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    recipeOrder.modifyTime = this.datePipe
                        .transform(recipeOrder.modifyTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.recipeOrderModalRef(component, recipeOrder);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.recipeOrderModalRef(component, new RecipeOrder());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    recipeOrderModalRef(component: Component, recipeOrder: RecipeOrder): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.recipeOrder = recipeOrder;
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
