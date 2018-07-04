import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Pics } from './pics.model';
import { PicsPopupService } from './pics-popup.service';
import { PicsService } from './pics.service';

@Component({
    selector: 'jhi-pics-delete-dialog',
    templateUrl: './pics-delete-dialog.component.html'
})
export class PicsDeleteDialogComponent {

    pics: Pics;

    constructor(
        private picsService: PicsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.picsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'picsListModification',
                content: 'Deleted an pics'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pics-delete-popup',
    template: ''
})
export class PicsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private picsPopupService: PicsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.picsPopupService
                .open(PicsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
