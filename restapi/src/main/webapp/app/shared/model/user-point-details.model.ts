import { Moment } from 'moment';

export const enum PointEventType {
    SALUTE = 'SALUTE',
    LOGIN = 'LOGIN',
    MESSAGE = 'MESSAGE',
    MESSAGEWITHIMAGE = 'MESSAGEWITHIMAGE',
    REPLY = 'REPLY',
    REPLYWITHIMAGE = 'REPLYWITHIMAGE',
    JIONACTION = 'JIONACTION',
    FOCUS = 'FOCUS',
    NEWFUN = 'NEWFUN',
    CARD = 'CARD',
    CARDWITHIMAGE = 'CARDWITHIMAGE',
    PUBILSHACTION = 'PUBILSHACTION',
    PUBILSHQUESTION = 'PUBILSHQUESTION',
    RECHARGE = 'RECHARGE',
    SYSRECHARGE = 'SYSRECHARGE',
    REDEEM = 'REDEEM'
}

export interface IUserPointDetails {
    id?: number;
    userid?: string;
    applyTime?: Moment;
    changePoint?: number;
    eventType?: PointEventType;
    descript?: string;
    targetSystem?: string;
    handleBy?: string;
    totalPoint?: number;
    validateString?: string;
    createBy?: string;
    createTime?: Moment;
    lastModifyBy?: string;
    lastModifyTime?: Moment;
    eventName?: string;
}

export class UserPointDetails implements IUserPointDetails {
    constructor(
        public id?: number,
        public userid?: string,
        public applyTime?: Moment,
        public changePoint?: number,
        public eventType?: PointEventType,
        public descript?: string,
        public targetSystem?: string,
        public handleBy?: string,
        public totalPoint?: number,
        public validateString?: string,
        public createBy?: string,
        public createTime?: Moment,
        public lastModifyBy?: string,
        public lastModifyTime?: Moment,
        public eventName?: string
    ) {}
}
