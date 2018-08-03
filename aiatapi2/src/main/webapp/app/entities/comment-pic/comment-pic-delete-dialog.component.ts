import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommentPic } from 'app/shared/model/comment-pic.model';
import { CommentPicService } from './comment-pic.service';

@Component({
    selector: 'jhi-comment-pic-delete-dialog',
    templateUrl: './comment-pic-delete-dialog.component.html'
})
export class CommentPicDeleteDialogComponent {
    commentPic: ICommentPic;

    constructor(private commentPicService: CommentPicService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commentPicService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commentPicListModification',
                content: 'Deleted an commentPic'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-comment-pic-delete-popup',
    template: ''
})
export class CommentPicDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commentPic }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommentPicDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.commentPic = commentPic;
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
