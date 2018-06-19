import { BaseEntity } from './../../shared';

export class WechatUser implements BaseEntity {
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
        public sex?: boolean,
        public companyRole?: string,
        public cookFlag?: boolean,
        public intesting?: string,
        public skill?: string,
        public createTime?: any,
        public modifyTime?: any,
    ) {
        this.sex = false;
        this.cookFlag = false;
    }
}
