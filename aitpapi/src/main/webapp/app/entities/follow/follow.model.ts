import { BaseEntity } from './../../shared';

export class Follow implements BaseEntity {
    constructor(
        public id?: number,
        public followUserId?: number,
        public followedUserId?: number,
        public createTime?: any,
        public modifyTime?: any,
    ) {
    }
}
