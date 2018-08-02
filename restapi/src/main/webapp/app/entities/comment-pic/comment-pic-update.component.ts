import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ICommentPic } from 'app/shared/model/comment-pic.model';
import { CommentPicService } from './comment-pic.service';
import { IComment } from 'app/shared/model/comment.model';
import { CommentService } from 'app/entities/comment';

@Component({
    selector: 'jhi-comment-pic-update',
    templateUrl: './comment-pic-update.component.html'
})
export class CommentPicUpdateComponent implements OnInit {
    private _commentPic: ICommentPic;
    isSaving: boolean;

    comments: IComment[];
    createTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private commentPicService: CommentPicService,
        private commentService: CommentService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commentPic }) => {
            this.commentPic = commentPic;
        });
        this.commentService.query().subscribe(
            (res: HttpResponse<IComment[]>) => {
                this.comments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.commentPic.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        if (this.commentPic.id !== undefined) {
            this.subscribeToSaveResponse(this.commentPicService.update(this.commentPic));
        } else {
            this.subscribeToSaveResponse(this.commentPicService.create(this.commentPic));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICommentPic>>) {
        result.subscribe((res: HttpResponse<ICommentPic>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCommentById(index: number, item: IComment) {
        return item.id;
    }
    get commentPic() {
        return this._commentPic;
    }

    set commentPic(commentPic: ICommentPic) {
        this._commentPic = commentPic;
        this.createTime = moment(commentPic.createTime).format(DATE_TIME_FORMAT);
    }
}
