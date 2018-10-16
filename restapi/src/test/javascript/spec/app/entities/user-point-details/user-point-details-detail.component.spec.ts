/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { UserPointDetailsDetailComponent } from 'app/entities/user-point-details/user-point-details-detail.component';
import { UserPointDetails } from 'app/shared/model/user-point-details.model';

describe('Component Tests', () => {
    describe('UserPointDetails Management Detail Component', () => {
        let comp: UserPointDetailsDetailComponent;
        let fixture: ComponentFixture<UserPointDetailsDetailComponent>;
        const route = ({ data: of({ userPointDetails: new UserPointDetails(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [UserPointDetailsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UserPointDetailsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserPointDetailsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.userPointDetails).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
