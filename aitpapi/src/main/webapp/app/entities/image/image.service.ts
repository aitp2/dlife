import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Image } from './image.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Image>;

@Injectable()
export class ImageService {

    private resourceUrl =  SERVER_API_URL + 'api/images';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(image: Image): Observable<EntityResponseType> {
        const copy = this.convert(image);
        return this.http.post<Image>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(image: Image): Observable<EntityResponseType> {
        const copy = this.convert(image);
        return this.http.put<Image>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Image>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Image[]>> {
        const options = createRequestOption(req);
        return this.http.get<Image[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Image[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Image = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Image[]>): HttpResponse<Image[]> {
        const jsonResponse: Image[] = res.body;
        const body: Image[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Image.
     */
    private convertItemFromServer(image: Image): Image {
        const copy: Image = Object.assign({}, image);
        copy.createTime = this.dateUtils
            .convertDateTimeFromServer(image.createTime);
        return copy;
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
