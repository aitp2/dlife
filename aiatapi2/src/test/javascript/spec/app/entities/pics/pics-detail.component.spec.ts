/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { PicsDetailComponent } from 'app/entities/pics/pics-detail.component';
import { Pics } from 'app/shared/model/pics.model';

describe('Component Tests', () => {
    describe('Pics Management Detail Component', () => {
        let comp: PicsDetailComponent;
        let fixture: ComponentFixture<PicsDetailComponent>;
        const route = ({ data: of({ pics: new Pics(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [PicsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PicsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PicsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pics).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
