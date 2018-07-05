import { BaseEntity } from './../../shared';

export const enum CommentChannel {
    'COOK',
    'FIT',
    'PIN'
}

export class Comment implements BaseEntity {
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
        public createTime?: any,
        public modifyTime?: any,
        public commentPics?: BaseEntity[],
    ) {
    }
}
