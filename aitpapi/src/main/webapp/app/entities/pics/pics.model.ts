import { BaseEntity } from './../../shared';

export class Pics implements BaseEntity {
    constructor(
        public id?: number,
        public ossPath?: string,
        public createTime?: any,
        public fitnessActivity?: BaseEntity,
        public clockIn?: BaseEntity,
    ) {
    }
}
