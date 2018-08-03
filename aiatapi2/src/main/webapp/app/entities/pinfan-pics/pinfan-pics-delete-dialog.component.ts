import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPinfanPics } from 'app/shared/model/pinfan-pics.model';
import { PinfanPicsService } from './pinfan-pics.service';

@Component({
    selector: 'jhi-pinfan-pics-delete-dialog',
    templateUrl: './pinfan-pics-delete-dialog.component.html'
})
export class PinfanPicsDeleteDialogComponent {
    pinfanPics: IPinfanPics;

    constructor(private pinfanPicsService: PinfanPicsService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pinfanPicsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pinfanPicsListModification',
                content: 'Deleted an pinfanPics'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pinfan-pics-delete-popup',
    template: ''
})
export class PinfanPicsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pinfanPics }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PinfanPicsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.pinfanPics = pinfanPics;
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
