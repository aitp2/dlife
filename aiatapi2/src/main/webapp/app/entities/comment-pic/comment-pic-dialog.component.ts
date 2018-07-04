import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CommentPic } from './comment-pic.model';
import { CommentPicPopupService } from './comment-pic-popup.service';
import { CommentPicService } from './comment-pic.service';
import { Comment, CommentService } from '../comment';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-comment-pic-dialog',
    templateUrl: './comment-pic-dialog.component.html'
})
export class CommentPicDialogComponent implements OnInit {

    commentPic: CommentPic;
    isSaving: boolean;

    comments: Comment[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private commentPicService: CommentPicService,
        private commentService: CommentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.commentService.query()
            .subscribe((res: ResponseWrapper) => { this.comments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.commentPic.id !== undefined) {
            this.subscribeToSaveResponse(
                this.commentPicService.update(this.commentPic));
        } else {
            this.subscribeToSaveResponse(
                this.commentPicService.create(this.commentPic));
        }
    }

    private subscribeToSaveResponse(result: Observable<CommentPic>) {
        result.subscribe((res: CommentPic) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CommentPic) {
        this.eventManager.broadcast({ name: 'commentPicListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCommentById(index: number, item: Comment) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-comment-pic-popup',
    template: ''
})
export class CommentPicPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commentPicPopupService: CommentPicPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.commentPicPopupService
                    .open(CommentPicDialogComponent as Component, params['id']);
            } else {
                this.commentPicPopupService
                    .open(CommentPicDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
