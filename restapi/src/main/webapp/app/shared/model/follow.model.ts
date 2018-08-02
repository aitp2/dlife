import { Moment } from 'moment';

export interface IFollow {
    id?: number;
    followUserId?: string;
    followUserNickname?: string;
    followUseravatar?: string;
    followedUserId?: string;
    followedUserNickname?: string;
    followedUseravatar?: string;
    createTime?: Moment;
    modifyTime?: Moment;
}

export class Follow implements IFollow {
    constructor(
        public id?: number,
        public followUserId?: string,
        public followUserNickname?: string,
        public followUseravatar?: string,
        public followedUserId?: string,
        public followedUserNickname?: string,
        public followedUseravatar?: string,
        public createTime?: Moment,
        public modifyTime?: Moment
    ) {}
}
