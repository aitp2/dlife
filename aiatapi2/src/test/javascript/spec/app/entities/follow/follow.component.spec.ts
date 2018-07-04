/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { AitpapiTestModule } from '../../../test.module';
import { FollowComponent } from '../../../../../../main/webapp/app/entities/follow/follow.component';
import { FollowService } from '../../../../../../main/webapp/app/entities/follow/follow.service';
import { Follow } from '../../../../../../main/webapp/app/entities/follow/follow.model';

describe('Component Tests', () => {

    describe('Follow Management Component', () => {
        let comp: FollowComponent;
        let fixture: ComponentFixture<FollowComponent>;
        let service: FollowService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [FollowComponent],
                providers: [
                    FollowService
                ]
            })
            .overrideTemplate(FollowComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FollowComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FollowService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Follow(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.follows[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
