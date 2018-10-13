import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITaskGroup } from 'app/shared/model/task-group.model';
import { TaskGroupService } from './task-group.service';

@Component({
    selector: 'jhi-task-group-update',
    templateUrl: './task-group-update.component.html'
})
export class TaskGroupUpdateComponent implements OnInit {
    private _taskGroup: ITaskGroup;
    isSaving: boolean;
    createTime: string;
    lastModifyTime: string;

    constructor(private taskGroupService: TaskGroupService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ taskGroup }) => {
            this.taskGroup = taskGroup;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.taskGroup.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        this.taskGroup.lastModifyTime = moment(this.lastModifyTime, DATE_TIME_FORMAT);
        if (this.taskGroup.id !== undefined) {
            this.subscribeToSaveResponse(this.taskGroupService.update(this.taskGroup));
        } else {
            this.subscribeToSaveResponse(this.taskGroupService.create(this.taskGroup));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITaskGroup>>) {
        result.subscribe((res: HttpResponse<ITaskGroup>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get taskGroup() {
        return this._taskGroup;
    }

    set taskGroup(taskGroup: ITaskGroup) {
        this._taskGroup = taskGroup;
        this.createTime = moment(taskGroup.createTime).format(DATE_TIME_FORMAT);
        this.lastModifyTime = moment(taskGroup.lastModifyTime).format(DATE_TIME_FORMAT);
    }
}
