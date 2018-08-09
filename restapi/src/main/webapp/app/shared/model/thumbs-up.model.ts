import { Moment } from 'moment';

export const enum ThumbsUpChannel {
    COOK = 'COOK',
    FIT = 'FIT',
    PIN = 'PIN',
    FAQS = 'FAQS'
}

export const enum ThumbsUpModule {
    COMMENT = 'COMMENT',
    ACTIVITY = 'ACTIVITY',
    USERCENTER = 'USERCENTER'
}

export interface IThumbsUp {
    id?: number;
    objectId?: number;
    channel?: ThumbsUpChannel;
    module?: ThumbsUpModule;
    wechatUserId?: string;
    avatar?: string;
    nickName?: string;
    createTime?: Moment;
    keyName_1?: string;
    keyName_2?: string;
}

export class ThumbsUp implements IThumbsUp {
    constructor(
        public id?: number,
        public objectId?: number,
        public channel?: ThumbsUpChannel,
        public module?: ThumbsUpModule,
        public wechatUserId?: string,
        public avatar?: string,
        public nickName?: string,
        public createTime?: Moment,
        public keyName_1?: string,
        public keyName_2?: string
    ) {}
}
