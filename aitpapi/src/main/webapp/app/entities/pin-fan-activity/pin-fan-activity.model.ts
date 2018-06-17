import { BaseEntity } from './../../shared';

export class PinFanActivity implements BaseEntity {
    constructor(
        public id?: number,
        public wechatUserId?: string,
        public activitiyType?: number,
        public descrption?: string,
        public organizeUser?: string,
        public coverPicture?: string,
        public appointDatetime?: any,
        public lowerLimit?: number,
        public upperLimit?: number,
        public payType?: string,
        public deadline?: any,
        public comment?: string,
        public isActive?: boolean,
        public attendees?: BaseEntity[],
        public rinfanPics?: BaseEntity[],
        public rates?: BaseEntity[],
    ) {
        this.isActive = false;
    }
}
