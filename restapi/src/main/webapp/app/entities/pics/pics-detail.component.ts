import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPics } from 'app/shared/model/pics.model';

@Component({
    selector: 'jhi-pics-detail',
    templateUrl: './pics-detail.component.html'
})
export class PicsDetailComponent implements OnInit {
    pics: IPics;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pics }) => {
            this.pics = pics;
        });
    }

    previousState() {
        window.history.back();
    }
}
