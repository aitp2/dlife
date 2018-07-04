import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ActivityParticipation } from './activity-participation.model';
import { ActivityParticipationPopupService } from './activity-participation-popup.service';
import { ActivityParticipationService } from './activity-participation.service';

@Component({
    selector: 'jhi-activity-participation-delete-dialog',
    templateUrl: './activity-participation-delete-dialog.component.html'
})
export class ActivityParticipationDeleteDialogComponent {

    activityParticipation: ActivityParticipation;

    constructor(
        private activityParticipationService: ActivityParticipationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.activityParticipationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'activityParticipationListModification',
                content: 'Deleted an activityParticipation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-activity-participation-delete-popup',
    template: ''
})
export class ActivityParticipationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private activityParticipationPopupService: ActivityParticipationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.activityParticipationPopupService
                .open(ActivityParticipationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
