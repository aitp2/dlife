/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { ThumbsUpDetailComponent } from 'app/entities/thumbs-up/thumbs-up-detail.component';
import { ThumbsUp } from 'app/shared/model/thumbs-up.model';

describe('Component Tests', () => {
    describe('ThumbsUp Management Detail Component', () => {
        let comp: ThumbsUpDetailComponent;
        let fixture: ComponentFixture<ThumbsUpDetailComponent>;
        const route = ({ data: of({ thumbsUp: new ThumbsUp(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [ThumbsUpDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ThumbsUpDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ThumbsUpDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.thumbsUp).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
