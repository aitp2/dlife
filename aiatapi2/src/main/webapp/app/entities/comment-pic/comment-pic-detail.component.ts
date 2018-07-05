import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CommentPic } from './comment-pic.model';
import { CommentPicService } from './comment-pic.service';

@Component({
    selector: 'jhi-comment-pic-detail',
    templateUrl: './comment-pic-detail.component.html'
})
export class CommentPicDetailComponent implements OnInit, OnDestroy {

    commentPic: CommentPic;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private commentPicService: CommentPicService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCommentPics();
    }

    load(id) {
        this.commentPicService.find(id).subscribe((commentPic) => {
            this.commentPic = commentPic;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCommentPics() {
        this.eventSubscriber = this.eventManager.subscribe(
            'commentPicListModification',
            (response) => this.load(this.commentPic.id)
        );
    }
}
