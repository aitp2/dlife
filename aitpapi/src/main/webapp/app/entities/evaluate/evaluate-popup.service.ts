import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Evaluate } from './evaluate.model';
import { EvaluateService } from './evaluate.service';

@Injectable()
export class EvaluatePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private evaluateService: EvaluateService

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
                this.evaluateService.find(id)
                    .subscribe((evaluateResponse: HttpResponse<Evaluate>) => {
                        const evaluate: Evaluate = evaluateResponse.body;
                        evaluate.createTime = this.datePipe
                            .transform(evaluate.createTime, 'yyyy-MM-ddTHH:mm:ss');
                        evaluate.modifyTime = this.datePipe
                            .transform(evaluate.modifyTime, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.evaluateModalRef(component, evaluate);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.evaluateModalRef(component, new Evaluate());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    evaluateModalRef(component: Component, evaluate: Evaluate): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.evaluate = evaluate;
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
