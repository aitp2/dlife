import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CommentPic } from './comment-pic.model';
import { CommentPicPopupService } from './comment-pic-popup.service';
import { CommentPicService } from './comment-pic.service';

@Component({
    selector: 'jhi-comment-pic-delete-dialog',
    templateUrl: './comment-pic-delete-dialog.component.html'
})
export class CommentPicDeleteDialogComponent {

    commentPic: CommentPic;

    constructor(
        private commentPicService: CommentPicService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commentPicService.delete(id).subscribe((response) => {
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

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commentPicPopupService: CommentPicPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.commentPicPopupService
                .open(CommentPicDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
