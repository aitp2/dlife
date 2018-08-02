/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { WechatUserUpdateComponent } from 'app/entities/wechat-user/wechat-user-update.component';
import { WechatUserService } from 'app/entities/wechat-user/wechat-user.service';
import { WechatUser } from 'app/shared/model/wechat-user.model';

describe('Component Tests', () => {
    describe('WechatUser Management Update Component', () => {
        let comp: WechatUserUpdateComponent;
        let fixture: ComponentFixture<WechatUserUpdateComponent>;
        let service: WechatUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [WechatUserUpdateComponent]
            })
                .overrideTemplate(WechatUserUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WechatUserUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatUserService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new WechatUser(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.wechatUser = entity;
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
                    const entity = new WechatUser();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.wechatUser = entity;
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
