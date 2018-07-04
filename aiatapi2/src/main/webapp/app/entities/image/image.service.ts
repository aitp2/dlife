import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Image } from './image.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ImageService {

    private resourceUrl =  SERVER_API_URL + 'api/images';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(image: Image): Observable<Image> {
        const copy = this.convert(image);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(image: Image): Observable<Image> {
        const copy = this.convert(image);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Image> {
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
     * Convert a returned JSON object to Image.
     */
    private convertItemFromServer(json: any): Image {
        const entity: Image = Object.assign(new Image(), json);
        entity.createTime = this.dateUtils
            .convertDateTimeFromServer(json.createTime);
        return entity;
    }

    /**
     * Convert a Image to a JSON which can be sent to the server.
     */
    private convert(image: Image): Image {
        const copy: Image = Object.assign({}, image);

        copy.createTime = this.dateUtils.toDate(image.createTime);
        return copy;
    }
}
