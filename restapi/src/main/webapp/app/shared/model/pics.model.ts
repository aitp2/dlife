import { Moment } from 'moment';

export interface IPics {
    id?: number;
    ossPath?: string;
    createTime?: Moment;
    fitnessActivityId?: number;
    clockInId?: number;
}

export class Pics implements IPics {
    constructor(
        public id?: number,
        public ossPath?: string,
        public createTime?: Moment,
        public fitnessActivityId?: number,
        public clockInId?: number
    ) {}
}
