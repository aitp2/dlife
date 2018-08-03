import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPinfanPics } from 'app/shared/model/pinfan-pics.model';

@Component({
    selector: 'jhi-pinfan-pics-detail',
    templateUrl: './pinfan-pics-detail.component.html'
})
export class PinfanPicsDetailComponent implements OnInit {
    pinfanPics: IPinfanPics;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pinfanPics }) => {
            this.pinfanPics = pinfanPics;
        });
    }

    previousState() {
        window.history.back();
    }
}
