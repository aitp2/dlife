/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { AitpapiTestModule } from '../../../test.module';
import { FollowDetailComponent } from '../../../../../../main/webapp/app/entities/follow/follow-detail.component';
import { FollowService } from '../../../../../../main/webapp/app/entities/follow/follow.service';
import { Follow } from '../../../../../../main/webapp/app/entities/follow/follow.model';

describe('Component Tests', () => {

    describe('Follow Management Detail Component', () => {
        let comp: FollowDetailComponent;
        let fixture: ComponentFixture<FollowDetailComponent>;
        let service: FollowService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [FollowDetailComponent],
                providers: [
                    FollowService
                ]
            })
            .overrideTemplate(FollowDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FollowDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FollowService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Follow(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.follow).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
