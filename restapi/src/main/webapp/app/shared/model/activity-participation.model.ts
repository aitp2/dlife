import { Moment } from 'moment';
import { IClockIn } from 'app/shared/model//clock-in.model';

export interface IActivityParticipation {
    id?: number;
    wechatUserId?: string;
    nickName?: string;
    avatar?: string;
    project?: string;
    participationTime?: Moment;
    totalParticipateDays?: number;
    totalClockinDays?: number;
    currentContinueDays?: number;
    longestContinueDays?: number;
    latestClockinTime?: Moment;
    clockIns?: IClockIn[];
    fitnessActivityId?: number;
}

export class ActivityParticipation implements IActivityParticipation {
    constructor(
        public id?: number,
        public wechatUserId?: string,
        public nickName?: string,
        public avatar?: string,
        public project?: string,
        public participationTime?: Moment,
        public totalParticipateDays?: number,
        public totalClockinDays?: number,
        public currentContinueDays?: number,
        public longestContinueDays?: number,
        public latestClockinTime?: Moment,
        public clockIns?: IClockIn[],
        public fitnessActivityId?: number
    ) {}
}
