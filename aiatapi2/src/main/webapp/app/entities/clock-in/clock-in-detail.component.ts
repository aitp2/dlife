import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClockIn } from 'app/shared/model/clock-in.model';

@Component({
    selector: 'jhi-clock-in-detail',
    templateUrl: './clock-in-detail.component.html'
})
export class ClockInDetailComponent implements OnInit {
    clockIn: IClockIn;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clockIn }) => {
            this.clockIn = clockIn;
        });
    }

    previousState() {
        window.history.back();
    }
}
