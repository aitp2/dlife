import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Evaluate } from './evaluate.model';
import { EvaluatePopupService } from './evaluate-popup.service';
import { EvaluateService } from './evaluate.service';

@Component({
    selector: 'jhi-evaluate-delete-dialog',
    templateUrl: './evaluate-delete-dialog.component.html'
})
export class EvaluateDeleteDialogComponent {

    evaluate: Evaluate;

    constructor(
        private evaluateService: EvaluateService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.evaluateService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'evaluateListModification',
                content: 'Deleted an evaluate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-evaluate-delete-popup',
    template: ''
})
export class EvaluateDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private evaluatePopupService: EvaluatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.evaluatePopupService
                .open(EvaluateDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
