import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IThumbsUp } from 'app/shared/model/thumbs-up.model';

@Component({
    selector: 'jhi-thumbs-up-detail',
    templateUrl: './thumbs-up-detail.component.html'
})
export class ThumbsUpDetailComponent implements OnInit {
    thumbsUp: IThumbsUp;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ thumbsUp }) => {
            this.thumbsUp = thumbsUp;
        });
    }

    previousState() {
        window.history.back();
    }
}
