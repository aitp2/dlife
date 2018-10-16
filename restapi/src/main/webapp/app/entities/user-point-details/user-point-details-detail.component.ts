import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserPointDetails } from 'app/shared/model/user-point-details.model';

@Component({
    selector: 'jhi-user-point-details-detail',
    templateUrl: './user-point-details-detail.component.html'
})
export class UserPointDetailsDetailComponent implements OnInit {
    userPointDetails: IUserPointDetails;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userPointDetails }) => {
            this.userPointDetails = userPointDetails;
        });
    }

    previousState() {
        window.history.back();
    }
}
