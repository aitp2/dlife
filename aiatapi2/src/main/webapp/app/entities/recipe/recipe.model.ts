import { BaseEntity } from './../../shared';

export class Recipe implements BaseEntity {
    constructor(
        public id?: number,
        public wechatUserId?: string,
        public avatar?: string,
        public nickName?: string,
        public title?: string,
        public content?: string,
        public startTime?: any,
        public endTime?: any,
        public price?: number,
        public num?: number,
        public status?: number,
        public publishVersion?: number,
        public hot?: number,
        public createTime?: any,
        public modifyTime?: any,
        public recipeOrders?: BaseEntity[],
        public images?: BaseEntity[],
    ) {
    }
}
