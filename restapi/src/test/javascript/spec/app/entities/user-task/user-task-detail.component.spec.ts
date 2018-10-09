/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { UserTaskDetailComponent } from 'app/entities/user-task/user-task-detail.component';
import { UserTask } from 'app/shared/model/user-task.model';

describe('Component Tests', () => {
    describe('UserTask Management Detail Component', () => {
        let comp: UserTaskDetailComponent;
        let fixture: ComponentFixture<UserTaskDetailComponent>;
        const route = ({ data: of({ userTask: new UserTask(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [UserTaskDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UserTaskDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserTaskDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.userTask).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
