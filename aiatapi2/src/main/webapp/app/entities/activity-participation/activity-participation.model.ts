import { BaseEntity } from './../../shared';

export class ActivityParticipation implements BaseEntity {
    constructor(
        public id?: number,
        public wechatUserId?: string,
        public nickName?: string,
        public avatar?: string,
        public project?: string,
        public participationTime?: any,
        public clockIns?: BaseEntity[],
        public activityId?: number,
    ) {
    }
}
