import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Attendee } from './attendee.model';
import { AttendeeService } from './attendee.service';

@Component({
    selector: 'jhi-attendee-detail',
    templateUrl: './attendee-detail.component.html'
})
export class AttendeeDetailComponent implements OnInit, OnDestroy {

    attendee: Attendee;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private attendeeService: AttendeeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAttendees();
    }

    load(id) {
        this.attendeeService.find(id).subscribe((attendee) => {
            this.attendee = attendee;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAttendees() {
        this.eventSubscriber = this.eventManager.subscribe(
            'attendeeListModification',
            (response) => this.load(this.attendee.id)
        );
    }
}
