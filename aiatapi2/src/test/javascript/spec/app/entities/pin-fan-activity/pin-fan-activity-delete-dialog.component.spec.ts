/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { PinFanActivityDeleteDialogComponent } from 'app/entities/pin-fan-activity/pin-fan-activity-delete-dialog.component';
import { PinFanActivityService } from 'app/entities/pin-fan-activity/pin-fan-activity.service';

describe('Component Tests', () => {
  describe('PinFanActivity Management Delete Component', () => {
    let comp: PinFanActivityDeleteDialogComponent;
    let fixture: ComponentFixture<PinFanActivityDeleteDialogComponent>;
    let service: PinFanActivityService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AitpapiTestModule],
        declarations: [PinFanActivityDeleteDialogComponent]
      })
        .overrideTemplate(PinFanActivityDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PinFanActivityDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PinFanActivityService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
