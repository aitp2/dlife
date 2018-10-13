/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { TaskGroupDetailComponent } from 'app/entities/task-group/task-group-detail.component';
import { TaskGroup } from 'app/shared/model/task-group.model';

describe('Component Tests', () => {
    describe('TaskGroup Management Detail Component', () => {
        let comp: TaskGroupDetailComponent;
        let fixture: ComponentFixture<TaskGroupDetailComponent>;
        const route = ({ data: of({ taskGroup: new TaskGroup(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [TaskGroupDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TaskGroupDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TaskGroupDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.taskGroup).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
