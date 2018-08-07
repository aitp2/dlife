import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IThumbsUp } from 'app/shared/model/thumbs-up.model';
import { ThumbsUpService } from './thumbs-up.service';

@Component({
    selector: 'jhi-thumbs-up-update',
    templateUrl: './thumbs-up-update.component.html'
})
export class ThumbsUpUpdateComponent implements OnInit {
    private _thumbsUp: IThumbsUp;
    isSaving: boolean;
    createTime: string;

    constructor(private thumbsUpService: ThumbsUpService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ thumbsUp }) => {
            this.thumbsUp = thumbsUp;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.thumbsUp.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        if (this.thumbsUp.id !== undefined) {
            this.subscribeToSaveResponse(this.thumbsUpService.update(this.thumbsUp));
        } else {
            this.subscribeToSaveResponse(this.thumbsUpService.create(this.thumbsUp));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IThumbsUp>>) {
        result.subscribe((res: HttpResponse<IThumbsUp>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get thumbsUp() {
        return this._thumbsUp;
    }

    set thumbsUp(thumbsUp: IThumbsUp) {
        this._thumbsUp = thumbsUp;
        this.createTime = moment(thumbsUp.createTime).format(DATE_TIME_FORMAT);
    }
}
