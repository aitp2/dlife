import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISystemTotalPoints } from 'app/shared/model/system-total-points.model';
import { SystemTotalPointsService } from './system-total-points.service';

@Component({
    selector: 'jhi-system-total-points-delete-dialog',
    templateUrl: './system-total-points-delete-dialog.component.html'
})
export class SystemTotalPointsDeleteDialogComponent {
    systemTotalPoints: ISystemTotalPoints;

    constructor(
        private systemTotalPointsService: SystemTotalPointsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.systemTotalPointsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'systemTotalPointsListModification',
                content: 'Deleted an systemTotalPoints'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-system-total-points-delete-popup',
    template: ''
})
export class SystemTotalPointsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ systemTotalPoints }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SystemTotalPointsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.systemTotalPoints = systemTotalPoints;
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
