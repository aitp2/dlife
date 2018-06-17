import { BaseEntity } from './../../shared';

export class ClockIn implements BaseEntity {
    constructor(
        public id?: number,
        public signNote?: string,
        public punchDateTime?: any,
        public pics?: BaseEntity[],
        public activityParticipationId?: number,
    ) {
    }
}
