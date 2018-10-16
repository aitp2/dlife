import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IUserTask } from 'app/shared/model/user-task.model';
import { UserTaskService } from './user-task.service';
import { ITaskDefine } from 'app/shared/model/task-define.model';
import { TaskDefineService } from 'app/entities/task-define';

@Component({
    selector: 'jhi-user-task-update',
    templateUrl: './user-task-update.component.html'
})
export class UserTaskUpdateComponent implements OnInit {
    private _userTask: IUserTask;
    isSaving: boolean;

    taskdefines: ITaskDefine[];
    validateTo: string;
    createTime: string;
    lastModifyTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private userTaskService: UserTaskService,
        private taskDefineService: TaskDefineService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userTask }) => {
            this.userTask = userTask;
        });
        this.taskDefineService.query().subscribe(
            (res: HttpResponse<ITaskDefine[]>) => {
                this.taskdefines = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.userTask.validateTo = moment(this.validateTo, DATE_TIME_FORMAT);
        this.userTask.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        this.userTask.lastModifyTime = moment(this.lastModifyTime, DATE_TIME_FORMAT);
        if (this.userTask.id !== undefined) {
            this.subscribeToSaveResponse(this.userTaskService.update(this.userTask));
        } else {
            this.subscribeToSaveResponse(this.userTaskService.create(this.userTask));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUserTask>>) {
        result.subscribe((res: HttpResponse<IUserTask>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackTaskDefineById(index: number, item: ITaskDefine) {
        return item.id;
    }
    get userTask() {
        return this._userTask;
    }

    set userTask(userTask: IUserTask) {
        this._userTask = userTask;
        this.validateTo = moment(userTask.validateTo).format(DATE_TIME_FORMAT);
        this.createTime = moment(userTask.createTime).format(DATE_TIME_FORMAT);
        this.lastModifyTime = moment(userTask.lastModifyTime).format(DATE_TIME_FORMAT);
    }
}
