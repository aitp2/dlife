/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { FitnessActivityDeleteDialogComponent } from 'app/entities/fitness-activity/fitness-activity-delete-dialog.component';
import { FitnessActivityService } from 'app/entities/fitness-activity/fitness-activity.service';

describe('Component Tests', () => {
  describe('FitnessActivity Management Delete Component', () => {
    let comp: FitnessActivityDeleteDialogComponent;
    let fixture: ComponentFixture<FitnessActivityDeleteDialogComponent>;
    let service: FitnessActivityService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AitpapiTestModule],
        declarations: [FitnessActivityDeleteDialogComponent]
      })
        .overrideTemplate(FitnessActivityDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FitnessActivityDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FitnessActivityService);
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
