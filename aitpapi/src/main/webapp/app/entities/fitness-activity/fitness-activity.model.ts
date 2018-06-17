import { BaseEntity } from './../../shared';

export class FitnessActivity implements BaseEntity {
    constructor(
        public id?: number,
        public wechatUserId?: string,
        public title?: string,
        public descrption?: string,
        public signStartTime?: any,
        public signEndTime?: any,
        public activityStartTime?: any,
        public activityEndTime?: any,
        public activityParticipations?: BaseEntity[],
        public images?: BaseEntity[],
    ) {
    }
}
