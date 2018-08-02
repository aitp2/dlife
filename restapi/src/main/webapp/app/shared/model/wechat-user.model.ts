import { Moment } from 'moment';

export interface IWechatUser {
    id?: number;
    openId?: string;
    wechatCode?: string;
    userName?: string;
    nickName?: string;
    avatar?: string;
    mobileNum?: string;
    project?: string;
    seat?: string;
    introduce?: string;
    sex?: number;
    companyRole?: string;
    cookFlag?: number;
    intesting?: string;
    skill?: string;
    createTime?: Moment;
    modifyTime?: Moment;
}

export class WechatUser implements IWechatUser {
    constructor(
        public id?: number,
        public openId?: string,
        public wechatCode?: string,
        public userName?: string,
        public nickName?: string,
        public avatar?: string,
        public mobileNum?: string,
        public project?: string,
        public seat?: string,
        public introduce?: string,
        public sex?: number,
        public companyRole?: string,
        public cookFlag?: number,
        public intesting?: string,
        public skill?: string,
        public createTime?: Moment,
        public modifyTime?: Moment
    ) {}
}
