import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFitnessActivity } from 'app/shared/model/fitness-activity.model';
import { FitnessActivityService } from './fitness-activity.service';

@Component({
    selector: 'jhi-fitness-activity-delete-dialog',
    templateUrl: './fitness-activity-delete-dialog.component.html'
})
export class FitnessActivityDeleteDialogComponent {
    fitnessActivity: IFitnessActivity;

    constructor(
        private fitnessActivityService: FitnessActivityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fitnessActivityService.delete(id).subscribe(response => {
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
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fitnessActivity }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FitnessActivityDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.fitnessActivity = fitnessActivity;
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
