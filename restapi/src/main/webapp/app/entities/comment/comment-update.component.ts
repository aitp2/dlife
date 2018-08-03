import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IComment } from 'app/shared/model/comment.model';
import { CommentService } from './comment.service';

@Component({
    selector: 'jhi-comment-update',
    templateUrl: './comment-update.component.html'
})
export class CommentUpdateComponent implements OnInit {
    private _comment: IComment;
    isSaving: boolean;
    createTime: string;
    modifyTime: string;

    constructor(private commentService: CommentService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ comment }) => {
            this.comment = comment;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.comment.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        this.comment.modifyTime = moment(this.modifyTime, DATE_TIME_FORMAT);
        if (this.comment.id !== undefined) {
            this.subscribeToSaveResponse(this.commentService.update(this.comment));
        } else {
            this.subscribeToSaveResponse(this.commentService.create(this.comment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IComment>>) {
        result.subscribe((res: HttpResponse<IComment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get comment() {
        return this._comment;
    }

    set comment(comment: IComment) {
        this._comment = comment;
        this.createTime = moment(comment.createTime).format(DATE_TIME_FORMAT);
        this.modifyTime = moment(comment.modifyTime).format(DATE_TIME_FORMAT);
    }
}
