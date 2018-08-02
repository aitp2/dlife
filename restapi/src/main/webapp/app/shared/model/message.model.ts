export interface IMessage {
    id?: number;
    wechatUserId?: string;
    read?: boolean;
    eventMessageId?: number;
}

export class Message implements IMessage {
    constructor(public id?: number, public wechatUserId?: string, public read?: boolean, public eventMessageId?: number) {
        this.read = false;
    }
}
