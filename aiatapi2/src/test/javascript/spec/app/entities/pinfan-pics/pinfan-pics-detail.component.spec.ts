/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AitpapiTestModule } from '../../../test.module';
import { PinfanPicsDetailComponent } from 'app/entities/pinfan-pics/pinfan-pics-detail.component';
import { PinfanPics } from 'app/shared/model/pinfan-pics.model';

describe('Component Tests', () => {
    describe('PinfanPics Management Detail Component', () => {
        let comp: PinfanPicsDetailComponent;
        let fixture: ComponentFixture<PinfanPicsDetailComponent>;
        const route = ({ data: of({ pinfanPics: new PinfanPics(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AitpapiTestModule],
                declarations: [PinfanPicsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PinfanPicsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PinfanPicsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pinfanPics).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
