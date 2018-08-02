import { Moment } from 'moment';
import { IQuestionPic } from 'app/shared/model//question-pic.model';

export interface IQuestion {
    id?: number;
    wechatUserId?: string;
    nickName?: string;
    avatar?: string;
    title?: string;
    description?: string;
    createTime?: Moment;
    answerCount?: number;
    readingCount?: number;
    questionPics?: IQuestionPic[];
}

export class Question implements IQuestion {
    constructor(
        public id?: number,
        public wechatUserId?: string,
        public nickName?: string,
        public avatar?: string,
        public title?: string,
        public description?: string,
        public createTime?: Moment,
        public answerCount?: number,
        public readingCount?: number,
        public questionPics?: IQuestionPic[]
    ) {}
}
