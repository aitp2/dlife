import { BaseEntity } from './../../shared';

export class Rates implements BaseEntity {
    constructor(
        public id?: number,
        public wechatUserId?: string,
        public avatar?: string,
        public nickName?: string,
        public comments?: string,
        public rating?: number,
        public createTime?: any,
        public modifyTime?: any,
        public pinfanPics?: BaseEntity[],
        public pinFanActivity?: BaseEntity,
    ) {
    }
}
