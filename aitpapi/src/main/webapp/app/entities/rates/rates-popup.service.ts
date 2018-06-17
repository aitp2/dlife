import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Rates } from './rates.model';
import { RatesService } from './rates.service';

@Injectable()
export class RatesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private ratesService: RatesService

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
                this.ratesService.find(id)
                    .subscribe((ratesResponse: HttpResponse<Rates>) => {
                        const rates: Rates = ratesResponse.body;
                        rates.createTime = this.datePipe
                            .transform(rates.createTime, 'yyyy-MM-ddTHH:mm:ss');
                        rates.modifyTime = this.datePipe
                            .transform(rates.modifyTime, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.ratesModalRef(component, rates);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.ratesModalRef(component, new Rates());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ratesModalRef(component: Component, rates: Rates): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.rates = rates;
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
