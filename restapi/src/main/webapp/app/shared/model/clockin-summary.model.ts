import { Moment } from 'moment';

export interface IClockinSummary {
    id?: number;
    wechatUserId?: string;
    totallyCount?: number;
    weeklyCount?: number;
    serialCount?: number;
    lastClockInTime?: Moment;
}

export class ClockinSummary implements IClockinSummary {
    constructor(
        public id?: number,
        public wechatUserId?: string,
        public totallyCount?: number,
        public weeklyCount?: number,
        public serialCount?: number,
        public lastClockInTime?: Moment
    ) {}
}
