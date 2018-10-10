import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITaskDefine } from 'app/shared/model/task-define.model';
import { TaskDefineService } from './task-define.service';

@Component({
    selector: 'jhi-task-define-update',
    templateUrl: './task-define-update.component.html'
})
export class TaskDefineUpdateComponent implements OnInit {
    private _taskDefine: ITaskDefine;
    isSaving: boolean;
    createTime: string;
    lastModifyTime: string;

    constructor(private taskDefineService: TaskDefineService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ taskDefine }) => {
            this.taskDefine = taskDefine;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.taskDefine.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        this.taskDefine.lastModifyTime = moment(this.lastModifyTime, DATE_TIME_FORMAT);
        if (this.taskDefine.id !== undefined) {
            this.subscribeToSaveResponse(this.taskDefineService.update(this.taskDefine));
        } else {
            this.subscribeToSaveResponse(this.taskDefineService.create(this.taskDefine));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITaskDefine>>) {
        result.subscribe((res: HttpResponse<ITaskDefine>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get taskDefine() {
        return this._taskDefine;
    }

    set taskDefine(taskDefine: ITaskDefine) {
        this._taskDefine = taskDefine;
        this.createTime = moment(taskDefine.createTime).format(DATE_TIME_FORMAT);
        this.lastModifyTime = moment(taskDefine.lastModifyTime).format(DATE_TIME_FORMAT);
    }
}
