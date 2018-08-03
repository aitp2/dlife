import { Moment } from 'moment';

export interface IRecipeOrder {
    id?: number;
    wechatUserId?: string;
    avatar?: string;
    nickName?: string;
    recipeVersion?: number;
    price?: number;
    createTime?: Moment;
    modifyTime?: Moment;
    recipeId?: number;
}

export class RecipeOrder implements IRecipeOrder {
    constructor(
        public id?: number,
        public wechatUserId?: string,
        public avatar?: string,
        public nickName?: string,
        public recipeVersion?: number,
        public price?: number,
        public createTime?: Moment,
        public modifyTime?: Moment,
        public recipeId?: number
    ) {}
}
