import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaskDefine } from 'app/shared/model/task-define.model';
import { TaskDefineService } from './task-define.service';

@Component({
    selector: 'jhi-task-define-delete-dialog',
    templateUrl: './task-define-delete-dialog.component.html'
})
export class TaskDefineDeleteDialogComponent {
    taskDefine: ITaskDefine;

    constructor(private taskDefineService: TaskDefineService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.taskDefineService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'taskDefineListModification',
                content: 'Deleted an taskDefine'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-task-define-delete-popup',
    template: ''
})
export class TaskDefineDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ taskDefine }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TaskDefineDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.taskDefine = taskDefine;
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
