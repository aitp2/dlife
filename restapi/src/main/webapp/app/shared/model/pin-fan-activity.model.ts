import { Moment } from 'moment';
import { IAttendee } from 'app/shared/model//attendee.model';
import { IPinfanPics } from 'app/shared/model//pinfan-pics.model';

export interface IPinFanActivity {
    id?: number;
    wechatUserId?: string;
    avatar?: string;
    nickName?: string;
    activitiyType?: number;
    activitiyTile?: string;
    budget?: number;
    activitiyAddre?: string;
    descrption?: string;
    organizeUser?: string;
    coverPicture?: string;
    appointDatetime?: Moment;
    appointEndDatetime?: Moment;
    salerUrl?: string;
    lowerLimit?: number;
    upperLimit?: number;
    payType?: string;
    deadline?: Moment;
    comment?: string;
    status?: number;
    commentCount?: number;
    readingCount?: number;
    modifyTime?: Moment;
    attendees?: IAttendee[];
    pinfanPics?: IPinfanPics[];
}

export class PinFanActivity implements IPinFanActivity {
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
        public appointDatetime?: Moment,
        public appointEndDatetime?: Moment,
        public salerUrl?: string,
        public lowerLimit?: number,
        public upperLimit?: number,
        public payType?: string,
        public deadline?: Moment,
        public comment?: string,
        public status?: number,
        public commentCount?: number,
        public readingCount?: number,
        public modifyTime?: Moment,
        public attendees?: IAttendee[],
        public pinfanPics?: IPinfanPics[]
    ) {}
}
