import { Moment } from 'moment';

export interface IPinfanPics {
    id?: number;
    ossPath?: string;
    createTime?: Moment;
    pinFanActivityId?: number;
}

export class PinfanPics implements IPinfanPics {
    constructor(public id?: number, public ossPath?: string, public createTime?: Moment, public pinFanActivityId?: number) {}
}
