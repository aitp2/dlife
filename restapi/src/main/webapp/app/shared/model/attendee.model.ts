import { Moment } from 'moment';

export interface IAttendee {
    id?: number;
    wechatUserId?: string;
    nickName?: string;
    avatar?: string;
    participationTime?: Moment;
    activitiyTile?: string;
    pinFanActivityId?: number;
}

export class Attendee implements IAttendee {
    constructor(
        public id?: number,
        public wechatUserId?: string,
        public nickName?: string,
        public avatar?: string,
        public participationTime?: Moment,
        public activitiyTile?: string,
        public pinFanActivityId?: number
    ) {}
}
