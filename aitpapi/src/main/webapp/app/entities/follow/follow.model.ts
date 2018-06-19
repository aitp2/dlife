import { BaseEntity } from './../../shared';

export class Follow implements BaseEntity {
    constructor(
        public id?: number,
        public followUserId?: number,
        public followUserNickname?: string,
        public followUseravatar?: string,
        public followedUserId?: number,
        public followedUserNickname?: string,
        public followedUseravatar?: string,
        public createTime?: any,
        public modifyTime?: any,
    ) {
    }
}
