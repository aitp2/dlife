import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Attendee } from './attendee.model';
import { AttendeePopupService } from './attendee-popup.service';
import { AttendeeService } from './attendee.service';

@Component({
    selector: 'jhi-attendee-delete-dialog',
    templateUrl: './attendee-delete-dialog.component.html'
})
export class AttendeeDeleteDialogComponent {

    attendee: Attendee;

    constructor(
        private attendeeService: AttendeeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.attendeeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'attendeeListModification',
                content: 'Deleted an attendee'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-attendee-delete-popup',
    template: ''
})
export class AttendeeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private attendeePopupService: AttendeePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.attendeePopupService
                .open(AttendeeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
