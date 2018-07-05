import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PinfanPics } from './pinfan-pics.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PinfanPicsService {

    private resourceUrl =  SERVER_API_URL + 'api/pinfan-pics';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(pinfanPics: PinfanPics): Observable<PinfanPics> {
        const copy = this.convert(pinfanPics);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(pinfanPics: PinfanPics): Observable<PinfanPics> {
        const copy = this.convert(pinfanPics);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PinfanPics> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to PinfanPics.
     */
    private convertItemFromServer(json: any): PinfanPics {
        const entity: PinfanPics = Object.assign(new PinfanPics(), json);
        entity.createTime = this.dateUtils
            .convertDateTimeFromServer(json.createTime);
        return entity;
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
