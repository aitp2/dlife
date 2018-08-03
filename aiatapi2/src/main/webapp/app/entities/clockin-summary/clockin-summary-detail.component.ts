import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClockinSummary } from 'app/shared/model/clockin-summary.model';

@Component({
    selector: 'jhi-clockin-summary-detail',
    templateUrl: './clockin-summary-detail.component.html'
})
export class ClockinSummaryDetailComponent implements OnInit {
    clockinSummary: IClockinSummary;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clockinSummary }) => {
            this.clockinSummary = clockinSummary;
        });
    }

    previousState() {
        window.history.back();
    }
}
