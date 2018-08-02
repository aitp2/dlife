import { Moment } from 'moment';

export interface ICommentPic {
    id?: number;
    ossPath?: string;
    createTime?: Moment;
    commentId?: number;
}

export class CommentPic implements ICommentPic {
    constructor(public id?: number, public ossPath?: string, public createTime?: Moment, public commentId?: number) {}
}
