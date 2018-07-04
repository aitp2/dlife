import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ClockIn } from './clock-in.model';
import { ClockInPopupService } from './clock-in-popup.service';
import { ClockInService } from './clock-in.service';

@Component({
    selector: 'jhi-clock-in-delete-dialog',
    templateUrl: './clock-in-delete-dialog.component.html'
})
export class ClockInDeleteDialogComponent {

    clockIn: ClockIn;

    constructor(
        private clockInService: ClockInService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clockInService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clockInListModification',
                content: 'Deleted an clockIn'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-clock-in-delete-popup',
    template: ''
})
export class ClockInDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clockInPopupService: ClockInPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.clockInPopupService
                .open(ClockInDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
