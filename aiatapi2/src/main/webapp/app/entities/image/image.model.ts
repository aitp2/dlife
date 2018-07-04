import { BaseEntity } from './../../shared';

export class Image implements BaseEntity {
    constructor(
        public id?: number,
        public ossPath?: string,
        public createTime?: any,
        public recipeId?: number,
    ) {
    }
}
