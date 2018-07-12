import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFitnessActivity } from 'app/shared/model/fitness-activity.model';

@Component({
  selector: 'jhi-fitness-activity-detail',
  templateUrl: './fitness-activity-detail.component.html'
})
export class FitnessActivityDetailComponent implements OnInit {
  fitnessActivity: IFitnessActivity;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fitnessActivity }) => {
      this.fitnessActivity = fitnessActivity;
    });
  }

  previousState() {
    window.history.back();
  }
}
