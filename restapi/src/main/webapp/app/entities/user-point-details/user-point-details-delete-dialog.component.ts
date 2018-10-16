import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserPointDetails } from 'app/shared/model/user-point-details.model';
import { UserPointDetailsService } from './user-point-details.service';

@Component({
    selector: 'jhi-user-point-details-delete-dialog',
    templateUrl: './user-point-details-delete-dialog.component.html'
})
export class UserPointDetailsDeleteDialogComponent {
    userPointDetails: IUserPointDetails;

    constructor(
        private userPointDetailsService: UserPointDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userPointDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'userPointDetailsListModification',
                content: 'Deleted an userPointDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-point-details-delete-popup',
    template: ''
})
export class UserPointDetailsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userPointDetails }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UserPointDetailsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.userPointDetails = userPointDetails;
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
