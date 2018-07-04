import { BaseEntity } from './../../shared';

export class PinfanPics implements BaseEntity {
    constructor(
        public id?: number,
        public ossPath?: string,
        public createTime?: any,
        public pinFanActivityId?: number,
    ) {
    }
}
