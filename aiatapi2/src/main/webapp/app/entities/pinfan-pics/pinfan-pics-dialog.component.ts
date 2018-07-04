import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PinfanPics } from './pinfan-pics.model';
import { PinfanPicsPopupService } from './pinfan-pics-popup.service';
import { PinfanPicsService } from './pinfan-pics.service';
import { PinFanActivity, PinFanActivityService } from '../pin-fan-activity';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-pinfan-pics-dialog',
    templateUrl: './pinfan-pics-dialog.component.html'
})
export class PinfanPicsDialogComponent implements OnInit {

    pinfanPics: PinfanPics;
    isSaving: boolean;

    pinfanactivities: PinFanActivity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private pinfanPicsService: PinfanPicsService,
        private pinFanActivityService: PinFanActivityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.pinFanActivityService.query()
            .subscribe((res: ResponseWrapper) => { this.pinfanactivities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pinfanPics.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pinfanPicsService.update(this.pinfanPics));
        } else {
            this.subscribeToSaveResponse(
                this.pinfanPicsService.create(this.pinfanPics));
        }
    }

    private subscribeToSaveResponse(result: Observable<PinfanPics>) {
        result.subscribe((res: PinfanPics) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PinfanPics) {
        this.eventManager.broadcast({ name: 'pinfanPicsListModification', content: 'OK'});
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
    selector: 'jhi-pinfan-pics-popup',
    template: ''
})
export class PinfanPicsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pinfanPicsPopupService: PinfanPicsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pinfanPicsPopupService
                    .open(PinfanPicsDialogComponent as Component, params['id']);
            } else {
                this.pinfanPicsPopupService
                    .open(PinfanPicsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
