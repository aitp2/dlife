import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Follow } from './follow.model';
import { FollowPopupService } from './follow-popup.service';
import { FollowService } from './follow.service';

@Component({
    selector: 'jhi-follow-dialog',
    templateUrl: './follow-dialog.component.html'
})
export class FollowDialogComponent implements OnInit {

    follow: Follow;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private followService: FollowService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.follow.id !== undefined) {
            this.subscribeToSaveResponse(
                this.followService.update(this.follow));
        } else {
            this.subscribeToSaveResponse(
                this.followService.create(this.follow));
        }
    }

    private subscribeToSaveResponse(result: Observable<Follow>) {
        result.subscribe((res: Follow) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Follow) {
        this.eventManager.broadcast({ name: 'followListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-follow-popup',
    template: ''
})
export class FollowPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private followPopupService: FollowPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.followPopupService
                    .open(FollowDialogComponent as Component, params['id']);
            } else {
                this.followPopupService
                    .open(FollowDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
