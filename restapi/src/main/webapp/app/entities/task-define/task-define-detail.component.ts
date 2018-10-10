import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaskDefine } from 'app/shared/model/task-define.model';

@Component({
    selector: 'jhi-task-define-detail',
    templateUrl: './task-define-detail.component.html'
})
export class TaskDefineDetailComponent implements OnInit {
    taskDefine: ITaskDefine;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ taskDefine }) => {
            this.taskDefine = taskDefine;
        });
    }

    previousState() {
        window.history.back();
    }
}
