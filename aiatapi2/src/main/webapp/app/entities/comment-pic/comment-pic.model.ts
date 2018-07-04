import { BaseEntity } from './../../shared';

export class CommentPic implements BaseEntity {
    constructor(
        public id?: number,
        public ossPath?: string,
        public createTime?: any,
        public commentId?: number,
    ) {
    }
}
