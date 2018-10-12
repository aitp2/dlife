import { Moment } from 'moment';

export interface IUserTask {
    id?: number;
    userid?: string;
    taskStatus?: number;
    gainPoint?: number;
    remainPoint?: number;
    validateTo?: Moment;
    createBy?: string;
    createTime?: Moment;
    lastModifyBy?: string;
    lastModifyTime?: Moment;
    groupid?: string;
    groupName?: string;
    taskId?: number;
}

export class UserTask implements IUserTask {
    constructor(
        public id?: number,
        public userid?: string,
        public taskStatus?: number,
        public gainPoint?: number,
        public remainPoint?: number,
        public validateTo?: Moment,
        public createBy?: string,
        public createTime?: Moment,
        public lastModifyBy?: string,
        public lastModifyTime?: Moment,
        public groupid?: string,
        public groupName?: string,
        public taskId?: number
    ) {}
}
