/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { TaskDefineUpdateComponent } from 'app/entities/task-define/task-define-update.component';
import { TaskDefineService } from 'app/entities/task-define/task-define.service';
import { TaskDefine } from 'app/shared/model/task-define.model';

describe('Component Tests', () => {
    describe('TaskDefine Management Update Component', () => {
        let comp: TaskDefineUpdateComponent;
        let fixture: ComponentFixture<TaskDefineUpdateComponent>;
        let service: TaskDefineService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [TaskDefineUpdateComponent]
            })
                .overrideTemplate(TaskDefineUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TaskDefineUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaskDefineService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TaskDefine(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.taskDefine = entity;
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
                    const entity = new TaskDefine();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.taskDefine = entity;
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
