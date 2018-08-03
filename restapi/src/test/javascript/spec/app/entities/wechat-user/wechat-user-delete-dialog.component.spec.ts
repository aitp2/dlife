/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AitpapiTestModule } from '../../../test.module';
import { WechatUserDeleteDialogComponent } from 'app/entities/wechat-user/wechat-user-delete-dialog.component';
import { WechatUserService } from 'app/entities/wechat-user/wechat-user.service';

describe('Component Tests', () => {
    describe('WechatUser Management Delete Component', () => {
        let comp: WechatUserDeleteDialogComponent;
        let fixture: ComponentFixture<WechatUserDeleteDialogComponent>;
        let service: WechatUserService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [WechatUserDeleteDialogComponent]
            })
                .overrideTemplate(WechatUserDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WechatUserDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WechatUserService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
