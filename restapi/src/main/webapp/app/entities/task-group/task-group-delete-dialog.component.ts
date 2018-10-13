import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaskGroup } from 'app/shared/model/task-group.model';
import { TaskGroupService } from './task-group.service';

@Component({
    selector: 'jhi-task-group-delete-dialog',
    templateUrl: './task-group-delete-dialog.component.html'
})
export class TaskGroupDeleteDialogComponent {
    taskGroup: ITaskGroup;

    constructor(private taskGroupService: TaskGroupService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.taskGroupService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'taskGroupListModification',
                content: 'Deleted an taskGroup'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-task-group-delete-popup',
    template: ''
})
export class TaskGroupDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ taskGroup }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TaskGroupDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.taskGroup = taskGroup;
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
