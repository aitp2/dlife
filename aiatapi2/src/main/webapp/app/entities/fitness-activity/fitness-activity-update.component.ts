import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFitnessActivity } from 'app/shared/model/fitness-activity.model';
import { FitnessActivityService } from './fitness-activity.service';

@Component({
  selector: 'jhi-fitness-activity-update',
  templateUrl: './fitness-activity-update.component.html'
})
export class FitnessActivityUpdateComponent implements OnInit {
  private _fitnessActivity: IFitnessActivity;
  isSaving: boolean;
  signStartTime: string;
  signEndTime: string;
  activityStartTime: string;
  activityEndTime: string;
  modifyTime: string;

  constructor(private fitnessActivityService: FitnessActivityService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fitnessActivity }) => {
      this.fitnessActivity = fitnessActivity;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.fitnessActivity.signStartTime = moment(this.signStartTime, DATE_TIME_FORMAT);
    this.fitnessActivity.signEndTime = moment(this.signEndTime, DATE_TIME_FORMAT);
    this.fitnessActivity.activityStartTime = moment(this.activityStartTime, DATE_TIME_FORMAT);
    this.fitnessActivity.activityEndTime = moment(this.activityEndTime, DATE_TIME_FORMAT);
    this.fitnessActivity.modifyTime = moment(this.modifyTime, DATE_TIME_FORMAT);
    if (this.fitnessActivity.id !== undefined) {
      this.subscribeToSaveResponse(this.fitnessActivityService.update(this.fitnessActivity));
    } else {
      this.subscribeToSaveResponse(this.fitnessActivityService.create(this.fitnessActivity));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IFitnessActivity>>) {
    result.subscribe((res: HttpResponse<IFitnessActivity>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
  get fitnessActivity() {
    return this._fitnessActivity;
  }

  set fitnessActivity(fitnessActivity: IFitnessActivity) {
    this._fitnessActivity = fitnessActivity;
    this.signStartTime = moment(fitnessActivity.signStartTime).format(DATE_TIME_FORMAT);
    this.signEndTime = moment(fitnessActivity.signEndTime).format(DATE_TIME_FORMAT);
    this.activityStartTime = moment(fitnessActivity.activityStartTime).format(DATE_TIME_FORMAT);
    this.activityEndTime = moment(fitnessActivity.activityEndTime).format(DATE_TIME_FORMAT);
    this.modifyTime = moment(fitnessActivity.modifyTime).format(DATE_TIME_FORMAT);
  }
}
