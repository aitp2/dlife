import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserTask } from 'app/shared/model/user-task.model';

@Component({
    selector: 'jhi-user-task-detail',
    templateUrl: './user-task-detail.component.html'
})
export class UserTaskDetailComponent implements OnInit {
    userTask: IUserTask;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userTask }) => {
            this.userTask = userTask;
        });
    }

    previousState() {
        window.history.back();
    }
}
