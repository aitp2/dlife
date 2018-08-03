import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IAttendee } from 'app/shared/model/attendee.model';
import { AttendeeService } from './attendee.service';
import { IPinFanActivity } from 'app/shared/model/pin-fan-activity.model';
import { PinFanActivityService } from 'app/entities/pin-fan-activity';

@Component({
    selector: 'jhi-attendee-update',
    templateUrl: './attendee-update.component.html'
})
export class AttendeeUpdateComponent implements OnInit {
    private _attendee: IAttendee;
    isSaving: boolean;

    pinfanactivities: IPinFanActivity[];
    participationTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private attendeeService: AttendeeService,
        private pinFanActivityService: PinFanActivityService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ attendee }) => {
            this.attendee = attendee;
        });
        this.pinFanActivityService.query().subscribe(
            (res: HttpResponse<IPinFanActivity[]>) => {
                this.pinfanactivities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.attendee.participationTime = moment(this.participationTime, DATE_TIME_FORMAT);
        if (this.attendee.id !== undefined) {
            this.subscribeToSaveResponse(this.attendeeService.update(this.attendee));
        } else {
            this.subscribeToSaveResponse(this.attendeeService.create(this.attendee));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAttendee>>) {
        result.subscribe((res: HttpResponse<IAttendee>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPinFanActivityById(index: number, item: IPinFanActivity) {
        return item.id;
    }
    get attendee() {
        return this._attendee;
    }

    set attendee(attendee: IAttendee) {
        this._attendee = attendee;
        this.participationTime = moment(attendee.participationTime).format(DATE_TIME_FORMAT);
    }
}
