import { Moment } from 'moment';

export const enum TaskPeriod {
    DAILY = 'DAILY',
    WEEKLY = 'WEEKLY',
    MOTHLY = 'MOTHLY',
    FOREVER = 'FOREVER'
}

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

export interface ITaskDefine {
    id?: number;
    taskCode?: string;
    name?: string;
    status?: boolean;
    period?: TaskPeriod;
    maxlimit?: number;
    totalPoint?: number;
    targetSystems?: string;
    eventType?: PointEventType;
    conditions?: string;
    strategy?: string;
    applyStrategy?: string;
    priority?: number;
    createBy?: string;
    createTime?: Moment;
    lastModifyBy?: string;
    lastModifyTime?: Moment;
    groupid?: string;
}

export class TaskDefine implements ITaskDefine {
    constructor(
        public id?: number,
        public taskCode?: string,
        public name?: string,
        public status?: boolean,
        public period?: TaskPeriod,
        public maxlimit?: number,
        public totalPoint?: number,
        public targetSystems?: string,
        public eventType?: PointEventType,
        public conditions?: string,
        public strategy?: string,
        public applyStrategy?: string,
        public priority?: number,
        public createBy?: string,
        public createTime?: Moment,
        public lastModifyBy?: string,
        public lastModifyTime?: Moment,
        public groupid?: string
    ) {
        this.status = false;
    }
}
