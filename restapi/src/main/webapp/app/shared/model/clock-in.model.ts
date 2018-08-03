import { Moment } from 'moment';
import { IPics } from 'app/shared/model//pics.model';

export interface IClockIn {
    id?: number;
    title?: string;
    signNote?: string;
    punchDateTime?: Moment;
    activityId?: number;
    pics?: IPics[];
    activityParticipationId?: number;
}

export class ClockIn implements IClockIn {
    constructor(
        public id?: number,
        public title?: string,
        public signNote?: string,
        public punchDateTime?: Moment,
        public activityId?: number,
        public pics?: IPics[],
        public activityParticipationId?: number
    ) {}
}
