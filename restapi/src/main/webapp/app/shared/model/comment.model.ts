import { Moment } from 'moment';
import { ICommentPic } from 'app/shared/model//comment-pic.model';

export const enum CommentChannel {
    COOK = 'COOK',
    FIT = 'FIT',
    PIN = 'PIN',
    FAQS = 'FAQS'
}

export const enum CommentModule {
    COMMENT = 'COMMENT',
    ACTIVITY = 'ACTIVITY',
    USERCENTER = 'USERCENTER'
}

export interface IComment {
    id?: number;
    parentId?: number;
    objectId?: number;
    channel?: CommentChannel;
    wechatUserId?: string;
    avatar?: string;
    nickName?: string;
    content?: string;
    rating1?: number;
    rating2?: number;
    rating3?: number;
    createTime?: Moment;
    modifyTime?: Moment;
    module?: CommentModule;
    replyCount?: number;
    rpWechatUserId?: number;
    rpAvatar?: string;
    rpNickName?: string;
    commentPics?: ICommentPic[];
}

export class Comment implements IComment {
    constructor(
        public id?: number,
        public parentId?: number,
        public objectId?: number,
        public channel?: CommentChannel,
        public wechatUserId?: string,
        public avatar?: string,
        public nickName?: string,
        public content?: string,
        public rating1?: number,
        public rating2?: number,
        public rating3?: number,
        public createTime?: Moment,
        public modifyTime?: Moment,
        public module?: CommentModule,
        public replyCount?: number,
        public rpWechatUserId?: number,
        public rpAvatar?: string,
        public rpNickName?: string,
        public commentPics?: ICommentPic[]
    ) {}
}
