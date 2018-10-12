/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { TaskGroupUpdateComponent } from 'app/entities/task-group/task-group-update.component';
import { TaskGroupService } from 'app/entities/task-group/task-group.service';
import { TaskGroup } from 'app/shared/model/task-group.model';

describe('Component Tests', () => {
    describe('TaskGroup Management Update Component', () => {
        let comp: TaskGroupUpdateComponent;
        let fixture: ComponentFixture<TaskGroupUpdateComponent>;
        let service: TaskGroupService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [TaskGroupUpdateComponent]
            })
                .overrideTemplate(TaskGroupUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TaskGroupUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaskGroupService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TaskGroup(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.taskGroup = entity;
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
                    const entity = new TaskGroup();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.taskGroup = entity;
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
