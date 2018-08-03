import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionPic } from 'app/shared/model/question-pic.model';

@Component({
    selector: 'jhi-question-pic-detail',
    templateUrl: './question-pic-detail.component.html'
})
export class QuestionPicDetailComponent implements OnInit {
    questionPic: IQuestionPic;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ questionPic }) => {
            this.questionPic = questionPic;
        });
    }

    previousState() {
        window.history.back();
    }
}
