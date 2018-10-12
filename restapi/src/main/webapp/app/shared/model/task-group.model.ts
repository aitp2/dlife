import { Moment } from 'moment';

export interface ITaskGroup {
    id?: number;
    groupName?: string;
    description?: string;
    maxPoints?: number;
    maxCount?: number;
    createBy?: string;
    createTime?: Moment;
    lastModifyBy?: string;
    lastModifyTime?: Moment;
}

export class TaskGroup implements ITaskGroup {
    constructor(
        public id?: number,
        public groupName?: string,
        public description?: string,
        public maxPoints?: number,
        public maxCount?: number,
        public createBy?: string,
        public createTime?: Moment,
        public lastModifyBy?: string,
        public lastModifyTime?: Moment
    ) {}
}
