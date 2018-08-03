/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { EventMessageUpdateComponent } from 'app/entities/event-message/event-message-update.component';
import { EventMessageService } from 'app/entities/event-message/event-message.service';
import { EventMessage } from 'app/shared/model/event-message.model';

describe('Component Tests', () => {
    describe('EventMessage Management Update Component', () => {
        let comp: EventMessageUpdateComponent;
        let fixture: ComponentFixture<EventMessageUpdateComponent>;
        let service: EventMessageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [EventMessageUpdateComponent]
            })
                .overrideTemplate(EventMessageUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EventMessageUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EventMessageService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EventMessage(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.eventMessage = entity;
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
                    const entity = new EventMessage();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.eventMessage = entity;
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
