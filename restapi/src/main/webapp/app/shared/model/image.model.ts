import { Moment } from 'moment';

export interface IImage {
    id?: number;
    ossPath?: string;
    createTime?: Moment;
    recipeId?: number;
}

export class Image implements IImage {
    constructor(public id?: number, public ossPath?: string, public createTime?: Moment, public recipeId?: number) {}
}
