/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { EventMessageDetailComponent } from 'app/entities/event-message/event-message-detail.component';
import { EventMessage } from 'app/shared/model/event-message.model';

describe('Component Tests', () => {
    describe('EventMessage Management Detail Component', () => {
        let comp: EventMessageDetailComponent;
        let fixture: ComponentFixture<EventMessageDetailComponent>;
        const route = ({ data: of({ eventMessage: new EventMessage(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [EventMessageDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EventMessageDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EventMessageDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.eventMessage).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
