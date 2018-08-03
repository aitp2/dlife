import { Moment } from 'moment';

export interface IQuestionPic {
    id?: number;
    ossPath?: string;
    createTime?: Moment;
    questionId?: number;
}

export class QuestionPic implements IQuestionPic {
    constructor(public id?: number, public ossPath?: string, public createTime?: Moment, public questionId?: number) {}
}
