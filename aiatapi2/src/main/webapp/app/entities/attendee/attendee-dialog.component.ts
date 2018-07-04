import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Attendee } from './attendee.model';
import { AttendeePopupService } from './attendee-popup.service';
import { AttendeeService } from './attendee.service';
import { PinFanActivity, PinFanActivityService } from '../pin-fan-activity';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-attendee-dialog',
    templateUrl: './attendee-dialog.component.html'
})
export class AttendeeDialogComponent implements OnInit {

    attendee: Attendee;
    isSaving: boolean;

    pinfanactivities: PinFanActivity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private attendeeService: AttendeeService,
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
        if (this.attendee.id !== undefined) {
            this.subscribeToSaveResponse(
                this.attendeeService.update(this.attendee));
        } else {
            this.subscribeToSaveResponse(
                this.attendeeService.create(this.attendee));
        }
    }

    private subscribeToSaveResponse(result: Observable<Attendee>) {
        result.subscribe((res: Attendee) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Attendee) {
        this.eventManager.broadcast({ name: 'attendeeListModification', content: 'OK'});
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
    selector: 'jhi-attendee-popup',
    template: ''
})
export class AttendeePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private attendeePopupService: AttendeePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.attendeePopupService
                    .open(AttendeeDialogComponent as Component, params['id']);
            } else {
                this.attendeePopupService
                    .open(AttendeeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
