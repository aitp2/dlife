import { BaseEntity } from './../../shared';

export class ClockinSummary implements BaseEntity {
    constructor(
        public id?: number,
        public wechatUserId?: string,
        public totallyCount?: number,
        public weeklyCount?: number,
        public serialCount?: number,
        public lastClockInTime?: any,
    ) {
    }
}
