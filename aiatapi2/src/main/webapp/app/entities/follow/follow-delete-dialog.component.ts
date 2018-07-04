import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Follow } from './follow.model';
import { FollowPopupService } from './follow-popup.service';
import { FollowService } from './follow.service';

@Component({
    selector: 'jhi-follow-delete-dialog',
    templateUrl: './follow-delete-dialog.component.html'
})
export class FollowDeleteDialogComponent {

    follow: Follow;

    constructor(
        private followService: FollowService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.followService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'followListModification',
                content: 'Deleted an follow'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-follow-delete-popup',
    template: ''
})
export class FollowDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private followPopupService: FollowPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.followPopupService
                .open(FollowDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
