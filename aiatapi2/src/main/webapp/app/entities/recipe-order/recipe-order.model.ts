import { BaseEntity } from './../../shared';

export class RecipeOrder implements BaseEntity {
    constructor(
        public id?: number,
        public wechatUserId?: string,
        public avatar?: string,
        public nickName?: string,
        public recipeVersion?: number,
        public price?: number,
        public createTime?: any,
        public modifyTime?: any,
        public recipeId?: number,
    ) {
    }
}
