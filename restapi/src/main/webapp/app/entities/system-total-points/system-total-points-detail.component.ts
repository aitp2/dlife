import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISystemTotalPoints } from 'app/shared/model/system-total-points.model';

@Component({
    selector: 'jhi-system-total-points-detail',
    templateUrl: './system-total-points-detail.component.html'
})
export class SystemTotalPointsDetailComponent implements OnInit {
    systemTotalPoints: ISystemTotalPoints;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ systemTotalPoints }) => {
            this.systemTotalPoints = systemTotalPoints;
        });
    }

    previousState() {
        window.history.back();
    }
}
