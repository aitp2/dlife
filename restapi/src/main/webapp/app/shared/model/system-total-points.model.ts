import { Moment } from 'moment';

export interface ISystemTotalPoints {
    id?: number;
    systemId?: string;
    totalPoint?: number;
    createBy?: string;
    createTime?: Moment;
    lastModifyBy?: string;
    lastModifyTime?: Moment;
}

export class SystemTotalPoints implements ISystemTotalPoints {
    constructor(
        public id?: number,
        public systemId?: string,
        public totalPoint?: number,
        public createBy?: string,
        public createTime?: Moment,
        public lastModifyBy?: string,
        public lastModifyTime?: Moment
    ) {}
}
