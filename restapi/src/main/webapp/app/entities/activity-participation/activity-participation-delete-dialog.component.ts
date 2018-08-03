import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActivityParticipation } from 'app/shared/model/activity-participation.model';
import { ActivityParticipationService } from './activity-participation.service';

@Component({
    selector: 'jhi-activity-participation-delete-dialog',
    templateUrl: './activity-participation-delete-dialog.component.html'
})
export class ActivityParticipationDeleteDialogComponent {
    activityParticipation: IActivityParticipation;

    constructor(
        private activityParticipationService: ActivityParticipationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.activityParticipationService.delete(id).subscribe(response => {
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
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ activityParticipation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ActivityParticipationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.activityParticipation = activityParticipation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
