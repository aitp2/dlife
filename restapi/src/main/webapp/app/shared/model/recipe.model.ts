import { Moment } from 'moment';
import { IImage } from 'app/shared/model//image.model';
import { IRecipeOrder } from 'app/shared/model//recipe-order.model';

export interface IRecipe {
    id?: number;
    wechatUserId?: string;
    avatar?: string;
    nickName?: string;
    title?: string;
    content?: string;
    startTime?: Moment;
    endTime?: Moment;
    price?: number;
    num?: number;
    status?: number;
    publishVersion?: number;
    hot?: number;
    createTime?: Moment;
    modifyTime?: Moment;
    images?: IImage[];
    recipeOrders?: IRecipeOrder[];
}

export class Recipe implements IRecipe {
    constructor(
        public id?: number,
        public wechatUserId?: string,
        public avatar?: string,
        public nickName?: string,
        public title?: string,
        public content?: string,
        public startTime?: Moment,
        public endTime?: Moment,
        public price?: number,
        public num?: number,
        public status?: number,
        public publishVersion?: number,
        public hot?: number,
        public createTime?: Moment,
        public modifyTime?: Moment,
        public images?: IImage[],
        public recipeOrders?: IRecipeOrder[]
    ) {}
}
