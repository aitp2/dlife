import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from './question.service';

@Component({
    selector: 'jhi-question-update',
    templateUrl: './question-update.component.html'
})
export class QuestionUpdateComponent implements OnInit {
    private _question: IQuestion;
    isSaving: boolean;
    createTime: string;

    constructor(private questionService: QuestionService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ question }) => {
            this.question = question;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.question.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        if (this.question.id !== undefined) {
            this.subscribeToSaveResponse(this.questionService.update(this.question));
        } else {
            this.subscribeToSaveResponse(this.questionService.create(this.question));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuestion>>) {
        result.subscribe((res: HttpResponse<IQuestion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get question() {
        return this._question;
    }

    set question(question: IQuestion) {
        this._question = question;
        this.createTime = moment(question.createTime).format(DATE_TIME_FORMAT);
    }
}
