import { BaseEntity } from './../../shared';

export class Attendee implements BaseEntity {
    constructor(
        public id?: number,
        public wechatUserId?: string,
        public nickName?: string,
        public avatar?: string,
        public participationTime?: any,
        public activitiyTile?: string,
        public pinFanActivityId?: number,
    ) {
    }
}
