import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PinfanPics } from './pinfan-pics.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PinfanPics>;

@Injectable()
export class PinfanPicsService {

    private resourceUrl =  SERVER_API_URL + 'api/pinfan-pics';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(pinfanPics: PinfanPics): Observable<EntityResponseType> {
        const copy = this.convert(pinfanPics);
        return this.http.post<PinfanPics>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(pinfanPics: PinfanPics): Observable<EntityResponseType> {
        const copy = this.convert(pinfanPics);
        return this.http.put<PinfanPics>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PinfanPics>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PinfanPics[]>> {
        const options = createRequestOption(req);
        return this.http.get<PinfanPics[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PinfanPics[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PinfanPics = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PinfanPics[]>): HttpResponse<PinfanPics[]> {
        const jsonResponse: PinfanPics[] = res.body;
        const body: PinfanPics[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PinfanPics.
     */
    private convertItemFromServer(pinfanPics: PinfanPics): PinfanPics {
        const copy: PinfanPics = Object.assign({}, pinfanPics);
        copy.createTime = this.dateUtils
            .convertDateTimeFromServer(pinfanPics.createTime);
        return copy;
    }

    /**
     * Convert a PinfanPics to a JSON which can be sent to the server.
     */
    private convert(pinfanPics: PinfanPics): PinfanPics {
        const copy: PinfanPics = Object.assign({}, pinfanPics);

        copy.createTime = this.dateUtils.toDate(pinfanPics.createTime);
        return copy;
    }
}
