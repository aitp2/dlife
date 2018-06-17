import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Rates } from './rates.model';
import { RatesPopupService } from './rates-popup.service';
import { RatesService } from './rates.service';

@Component({
    selector: 'jhi-rates-delete-dialog',
    templateUrl: './rates-delete-dialog.component.html'
})
export class RatesDeleteDialogComponent {

    rates: Rates;

    constructor(
        private ratesService: RatesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ratesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ratesListModification',
                content: 'Deleted an rates'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rates-delete-popup',
    template: ''
})
export class RatesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ratesPopupService: RatesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ratesPopupService
                .open(RatesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
