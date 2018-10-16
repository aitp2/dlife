import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaskGroup } from 'app/shared/model/task-group.model';

@Component({
    selector: 'jhi-task-group-detail',
    templateUrl: './task-group-detail.component.html'
})
export class TaskGroupDetailComponent implements OnInit {
    taskGroup: ITaskGroup;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ taskGroup }) => {
            this.taskGroup = taskGroup;
        });
    }

    previousState() {
        window.history.back();
    }
}
