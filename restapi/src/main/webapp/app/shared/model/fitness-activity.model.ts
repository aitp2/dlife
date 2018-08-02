import { Moment } from 'moment';
import { IActivityParticipation } from 'app/shared/model//activity-participation.model';
import { IPics } from 'app/shared/model//pics.model';

export interface IFitnessActivity {
    id?: number;
    title?: string;
    descrption?: string;
    wechatUserId?: string;
    nickName?: string;
    avatar?: string;
    project?: string;
    companyRole?: string;
    signStartTime?: Moment;
    signEndTime?: Moment;
    activityStartTime?: Moment;
    activityEndTime?: Moment;
    commentCount?: number;
    modifyTime?: Moment;
    readingCount?: number;
    activityParticipations?: IActivityParticipation[];
    pics?: IPics[];
}

export class FitnessActivity implements IFitnessActivity {
    constructor(
        public id?: number,
        public title?: string,
        public descrption?: string,
        public wechatUserId?: string,
        public nickName?: string,
        public avatar?: string,
        public project?: string,
        public companyRole?: string,
        public signStartTime?: Moment,
        public signEndTime?: Moment,
        public activityStartTime?: Moment,
        public activityEndTime?: Moment,
        public commentCount?: number,
        public modifyTime?: Moment,
        public readingCount?: number,
        public activityParticipations?: IActivityParticipation[],
        public pics?: IPics[]
    ) {}
}
