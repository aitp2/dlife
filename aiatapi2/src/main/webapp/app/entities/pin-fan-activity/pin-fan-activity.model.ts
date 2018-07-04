import { BaseEntity } from './../../shared';

export class PinFanActivity implements BaseEntity {
    constructor(
        public id?: number,
        public wechatUserId?: string,
        public avatar?: string,
        public nickName?: string,
        public activitiyType?: number,
        public activitiyTile?: string,
        public budget?: number,
        public activitiyAddre?: string,
        public descrption?: string,
        public organizeUser?: string,
        public coverPicture?: string,
        public appointDatetime?: any,
        public appointEndDatetime?: any,
        public salerUrl?: string,
        public lowerLimit?: number,
        public upperLimit?: number,
        public payType?: string,
        public deadline?: any,
        public comment?: string,
        public status?: number,
        public commentCount?: number,
        public attendees?: BaseEntity[],
        public pinfanPics?: BaseEntity[],
    ) {
    }
}
