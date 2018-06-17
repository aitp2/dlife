import { BaseEntity } from './../../shared';

export class Evaluate implements BaseEntity {
    constructor(
        public id?: number,
        public parentId?: number,
        public content?: string,
        public tasteScore?: number,
        public serviceScore?: number,
        public createTime?: any,
        public modifyTime?: any,
        public images?: BaseEntity[],
        public recipeOrderId?: number,
    ) {
    }
}
