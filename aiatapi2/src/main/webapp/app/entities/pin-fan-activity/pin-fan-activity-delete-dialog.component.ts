import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PinFanActivity } from './pin-fan-activity.model';
import { PinFanActivityPopupService } from './pin-fan-activity-popup.service';
import { PinFanActivityService } from './pin-fan-activity.service';

@Component({
    selector: 'jhi-pin-fan-activity-delete-dialog',
    templateUrl: './pin-fan-activity-delete-dialog.component.html'
})
export class PinFanActivityDeleteDialogComponent {

    pinFanActivity: PinFanActivity;

    constructor(
        private pinFanActivityService: PinFanActivityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pinFanActivityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pinFanActivityListModification',
                content: 'Deleted an pinFanActivity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pin-fan-activity-delete-popup',
    template: ''
})
export class PinFanActivityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pinFanActivityPopupService: PinFanActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pinFanActivityPopupService
                .open(PinFanActivityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
