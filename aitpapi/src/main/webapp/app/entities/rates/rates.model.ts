import { BaseEntity } from './../../shared';

export class Rates implements BaseEntity {
    constructor(
        public id?: number,
        public comments?: string,
        public rating?: number,
        public createTime?: any,
        public modifyTime?: any,
        public rinfanPics?: BaseEntity[],
        public pinFanActivityId?: number,
    ) {
    }
}
