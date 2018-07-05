import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FitnessActivity } from './fitness-activity.model';
import { FitnessActivityPopupService } from './fitness-activity-popup.service';
import { FitnessActivityService } from './fitness-activity.service';

@Component({
    selector: 'jhi-fitness-activity-delete-dialog',
    templateUrl: './fitness-activity-delete-dialog.component.html'
})
export class FitnessActivityDeleteDialogComponent {

    fitnessActivity: FitnessActivity;

    constructor(
        private fitnessActivityService: FitnessActivityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fitnessActivityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fitnessActivityListModification',
                content: 'Deleted an fitnessActivity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fitness-activity-delete-popup',
    template: ''
})
export class FitnessActivityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fitnessActivityPopupService: FitnessActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.fitnessActivityPopupService
                .open(FitnessActivityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
