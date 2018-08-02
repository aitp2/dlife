import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionPic } from 'app/shared/model/question-pic.model';
import { QuestionPicService } from './question-pic.service';

@Component({
    selector: 'jhi-question-pic-delete-dialog',
    templateUrl: './question-pic-delete-dialog.component.html'
})
export class QuestionPicDeleteDialogComponent {
    questionPic: IQuestionPic;

    constructor(
        private questionPicService: QuestionPicService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.questionPicService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'questionPicListModification',
                content: 'Deleted an questionPic'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-question-pic-delete-popup',
    template: ''
})
export class QuestionPicDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ questionPic }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(QuestionPicDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.questionPic = questionPic;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
