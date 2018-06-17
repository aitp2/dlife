import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PinfanPics } from './pinfan-pics.model';
import { PinfanPicsPopupService } from './pinfan-pics-popup.service';
import { PinfanPicsService } from './pinfan-pics.service';

@Component({
    selector: 'jhi-pinfan-pics-delete-dialog',
    templateUrl: './pinfan-pics-delete-dialog.component.html'
})
export class PinfanPicsDeleteDialogComponent {

    pinfanPics: PinfanPics;

    constructor(
        private pinfanPicsService: PinfanPicsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pinfanPicsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pinfanPicsListModification',
                content: 'Deleted an pinfanPics'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pinfan-pics-delete-popup',
    template: ''
})
export class PinfanPicsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pinfanPicsPopupService: PinfanPicsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pinfanPicsPopupService
                .open(PinfanPicsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
