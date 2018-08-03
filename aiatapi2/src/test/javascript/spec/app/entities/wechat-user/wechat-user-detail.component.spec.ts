/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { WechatUserDetailComponent } from 'app/entities/wechat-user/wechat-user-detail.component';
import { WechatUser } from 'app/shared/model/wechat-user.model';

describe('Component Tests', () => {
    describe('WechatUser Management Detail Component', () => {
        let comp: WechatUserDetailComponent;
        let fixture: ComponentFixture<WechatUserDetailComponent>;
        const route = ({ data: of({ wechatUser: new WechatUser(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [WechatUserDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(WechatUserDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WechatUserDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.wechatUser).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
