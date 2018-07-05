/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { AitpapiTestModule } from '../../../test.module';
import { WechatUserComponent } from '../../../../../../main/webapp/app/entities/wechat-user/wechat-user.component';
import { WechatUserService } from '../../../../../../main/webapp/app/entities/wechat-user/wechat-user.service';
import { WechatUser } from '../../../../../../main/webapp/app/entities/wechat-user/wechat-user.model';

describe('Component Tests', () => {

    describe('WechatUser Management Component', () => {
        let comp: WechatUserComponent;
        let fixture: ComponentFixture<WechatUserComponent>;
        let service: WechatUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [WechatUserComponent],
                providers: [
                    WechatUserService
                ]
            })
            .overrideTemplate(WechatUserComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WechatUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatUserService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new WechatUser(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.wechatUsers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
