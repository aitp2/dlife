import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CommentPic } from './comment-pic.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CommentPicService {

    private resourceUrl =  SERVER_API_URL + 'api/comment-pics';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(commentPic: CommentPic): Observable<CommentPic> {
        const copy = this.convert(commentPic);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(commentPic: CommentPic): Observable<CommentPic> {
        const copy = this.convert(commentPic);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CommentPic> {
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
     * Convert a returned JSON object to CommentPic.
     */
    private convertItemFromServer(json: any): CommentPic {
        const entity: CommentPic = Object.assign(new CommentPic(), json);
        entity.createTime = this.dateUtils
            .convertDateTimeFromServer(json.createTime);
        return entity;
    }

    /**
     * Convert a CommentPic to a JSON which can be sent to the server.
     */
    private convert(commentPic: CommentPic): CommentPic {
        const copy: CommentPic = Object.assign({}, commentPic);

        copy.createTime = this.dateUtils.toDate(commentPic.createTime);
        return copy;
    }
}
