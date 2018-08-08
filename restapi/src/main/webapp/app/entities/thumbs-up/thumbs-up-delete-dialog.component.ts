import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IThumbsUp } from 'app/shared/model/thumbs-up.model';
import { ThumbsUpService } from './thumbs-up.service';

@Component({
    selector: 'jhi-thumbs-up-delete-dialog',
    templateUrl: './thumbs-up-delete-dialog.component.html'
})
export class ThumbsUpDeleteDialogComponent {
    thumbsUp: IThumbsUp;

    constructor(private thumbsUpService: ThumbsUpService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.thumbsUpService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'thumbsUpListModification',
                content: 'Deleted an thumbsUp'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-thumbs-up-delete-popup',
    template: ''
})
export class ThumbsUpDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ thumbsUp }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ThumbsUpDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.thumbsUp = thumbsUp;
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
