import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IQuestionPic } from 'app/shared/model/question-pic.model';
import { QuestionPicService } from './question-pic.service';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question';

@Component({
    selector: 'jhi-question-pic-update',
    templateUrl: './question-pic-update.component.html'
})
export class QuestionPicUpdateComponent implements OnInit {
    private _questionPic: IQuestionPic;
    isSaving: boolean;

    questions: IQuestion[];
    createTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private questionPicService: QuestionPicService,
        private questionService: QuestionService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ questionPic }) => {
            this.questionPic = questionPic;
        });
        this.questionService.query().subscribe(
            (res: HttpResponse<IQuestion[]>) => {
                this.questions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.questionPic.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        if (this.questionPic.id !== undefined) {
            this.subscribeToSaveResponse(this.questionPicService.update(this.questionPic));
        } else {
            this.subscribeToSaveResponse(this.questionPicService.create(this.questionPic));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionPic>>) {
        result.subscribe((res: HttpResponse<IQuestionPic>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackQuestionById(index: number, item: IQuestion) {
        return item.id;
    }
    get questionPic() {
        return this._questionPic;
    }

    set questionPic(questionPic: IQuestionPic) {
        this._questionPic = questionPic;
        this.createTime = moment(questionPic.createTime).format(DATE_TIME_FORMAT);
    }
}
