import { Moment } from 'moment';
import { IMessage } from 'app/shared/model//message.model';

export const enum EventType {
    CREATE = 'CREATE',
    ATTEND = 'ATTEND',
    QUIT = 'QUIT',
    CANCEL = 'CANCEL',
    UPDATE = 'UPDATE',
    COMMENT = 'COMMENT',
    CLOCKIN = 'CLOCKIN'
}

export const enum EventChannel {
    FITNESS = 'FITNESS',
    PINFAN = 'PINFAN',
    FAQS = 'FAQS'
}

export interface IEventMessage {
    id?: number;
    wechatUserId?: string;
    avatar?: string;
    nickName?: string;
    type?: EventType;
    channel?: EventChannel;
    objectId?: number;
    objectTitle?: string;
    createTime?: Moment;
    messages?: IMessage[];
}

export class EventMessage implements IEventMessage {
    constructor(
        public id?: number,
        public wechatUserId?: string,
        public avatar?: string,
        public nickName?: string,
        public type?: EventType,
        public channel?: EventChannel,
        public objectId?: number,
        public objectTitle?: string,
        public createTime?: Moment,
        public messages?: IMessage[]
    ) {}
}
