export const enum MessageType {
    FOLLOW = 'FOLLOW'
}

export interface IMessage {
    id?: number;
    wechatUserId?: string;
    read?: boolean;
    messageType?: MessageType;
    eventMessageId?: number;
}

export class Message implements IMessage {
    constructor(
        public id?: number,
        public wechatUserId?: string,
        public read?: boolean,
        public messageType?: MessageType,
        public eventMessageId?: number
    ) {
        this.read = false;
    }
}
