/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { AitpapiTestModule } from '../../../test.module';
import { WechatUserDetailComponent } from '../../../../../../main/webapp/app/entities/wechat-user/wechat-user-detail.component';
import { WechatUserService } from '../../../../../../main/webapp/app/entities/wechat-user/wechat-user.service';
import { WechatUser } from '../../../../../../main/webapp/app/entities/wechat-user/wechat-user.model';

describe('Component Tests', () => {

    describe('WechatUser Management Detail Component', () => {
        let comp: WechatUserDetailComponent;
        let fixture: ComponentFixture<WechatUserDetailComponent>;
        let service: WechatUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [WechatUserDetailComponent],
                providers: [
                    WechatUserService
                ]
            })
            .overrideTemplate(WechatUserDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatUserDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatUserService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new WechatUser(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.wechatUser).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
