import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPinFanActivity } from 'app/shared/model/pin-fan-activity.model';

@Component({
  selector: 'jhi-pin-fan-activity-detail',
  templateUrl: './pin-fan-activity-detail.component.html'
})
export class PinFanActivityDetailComponent implements OnInit {
  pinFanActivity: IPinFanActivity;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pinFanActivity }) => {
      this.pinFanActivity = pinFanActivity;
    });
  }

  previousState() {
    window.history.back();
  }
}
