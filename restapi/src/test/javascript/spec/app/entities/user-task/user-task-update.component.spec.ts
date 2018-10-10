/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { UserTaskUpdateComponent } from 'app/entities/user-task/user-task-update.component';
import { UserTaskService } from 'app/entities/user-task/user-task.service';
import { UserTask } from 'app/shared/model/user-task.model';

describe('Component Tests', () => {
    describe('UserTask Management Update Component', () => {
        let comp: UserTaskUpdateComponent;
        let fixture: ComponentFixture<UserTaskUpdateComponent>;
        let service: UserTaskService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [UserTaskUpdateComponent]
            })
                .overrideTemplate(UserTaskUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserTaskUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserTaskService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new UserTask(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.userTask = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new UserTask();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.userTask = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
