import { BaseEntity } from './../../shared';

export class FitnessActivity implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public descrption?: string,
        public wechatUserId?: string,
        public nickName?: string,
        public avatar?: string,
        public project?: string,
        public companyRole?: string,
        public signStartTime?: any,
        public signEndTime?: any,
        public activityStartTime?: any,
        public activityEndTime?: any,
        public commentCount?: number,
        public modifyTime?: any,
        public activityParticipations?: BaseEntity[],
        public images?: BaseEntity[],
    ) {
    }
}
