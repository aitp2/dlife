import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommentPic } from 'app/shared/model/comment-pic.model';

@Component({
    selector: 'jhi-comment-pic-detail',
    templateUrl: './comment-pic-detail.component.html'
})
export class CommentPicDetailComponent implements OnInit {
    commentPic: ICommentPic;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commentPic }) => {
            this.commentPic = commentPic;
        });
    }

    previousState() {
        window.history.back();
    }
}
