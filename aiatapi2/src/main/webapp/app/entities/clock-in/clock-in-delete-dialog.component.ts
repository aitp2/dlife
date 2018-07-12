import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClockIn } from 'app/shared/model/clock-in.model';
import { ClockInService } from './clock-in.service';

@Component({
  selector: 'jhi-clock-in-delete-dialog',
  templateUrl: './clock-in-delete-dialog.component.html'
})
export class ClockInDeleteDialogComponent {
  clockIn: IClockIn;

  constructor(private clockInService: ClockInService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.clockInService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'clockInListModification',
        content: 'Deleted an clockIn'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-clock-in-delete-popup',
  template: ''
})
export class ClockInDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ clockIn }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ClockInDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.clockIn = clockIn;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
