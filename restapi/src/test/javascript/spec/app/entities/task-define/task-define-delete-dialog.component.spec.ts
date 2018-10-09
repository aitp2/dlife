/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { TaskDefineDeleteDialogComponent } from 'app/entities/task-define/task-define-delete-dialog.component';
import { TaskDefineService } from 'app/entities/task-define/task-define.service';

describe('Component Tests', () => {
    describe('TaskDefine Management Delete Component', () => {
        let comp: TaskDefineDeleteDialogComponent;
        let fixture: ComponentFixture<TaskDefineDeleteDialogComponent>;
        let service: TaskDefineService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [TaskDefineDeleteDialogComponent]
            })
                .overrideTemplate(TaskDefineDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TaskDefineDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaskDefineService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
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
                )
            );
        });
    });
});
