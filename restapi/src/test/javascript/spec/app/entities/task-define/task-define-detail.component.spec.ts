/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { TaskDefineDetailComponent } from 'app/entities/task-define/task-define-detail.component';
import { TaskDefine } from 'app/shared/model/task-define.model';

describe('Component Tests', () => {
    describe('TaskDefine Management Detail Component', () => {
        let comp: TaskDefineDetailComponent;
        let fixture: ComponentFixture<TaskDefineDetailComponent>;
        const route = ({ data: of({ taskDefine: new TaskDefine(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [TaskDefineDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TaskDefineDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TaskDefineDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.taskDefine).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
