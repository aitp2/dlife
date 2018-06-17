import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Rates } from './rates.model';
import { RatesPopupService } from './rates-popup.service';
import { RatesService } from './rates.service';
import { PinFanActivity, PinFanActivityService } from '../pin-fan-activity';

@Component({
    selector: 'jhi-rates-dialog',
    templateUrl: './rates-dialog.component.html'
})
export class RatesDialogComponent implements OnInit {

    rates: Rates;
    isSaving: boolean;

    pinfanactivities: PinFanActivity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ratesService: RatesService,
        private pinFanActivityService: PinFanActivityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.pinFanActivityService.query()
            .subscribe((res: HttpResponse<PinFanActivity[]>) => { this.pinfanactivities = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rates.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ratesService.update(this.rates));
        } else {
            this.subscribeToSaveResponse(
                this.ratesService.create(this.rates));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Rates>>) {
        result.subscribe((res: HttpResponse<Rates>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Rates) {
        this.eventManager.broadcast({ name: 'ratesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPinFanActivityById(index: number, item: PinFanActivity) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rates-popup',
    template: ''
})
export class RatesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ratesPopupService: RatesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ratesPopupService
                    .open(RatesDialogComponent as Component, params['id']);
            } else {
                this.ratesPopupService
                    .open(RatesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
