import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Comment } from './comment.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CommentService {

    private resourceUrl =  SERVER_API_URL + 'api/comments';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(comment: Comment): Observable<Comment> {
        const copy = this.convert(comment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(comment: Comment): Observable<Comment> {
        const copy = this.convert(comment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Comment> {
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
     * Convert a returned JSON object to Comment.
     */
    private convertItemFromServer(json: any): Comment {
        const entity: Comment = Object.assign(new Comment(), json);
        entity.createTime = this.dateUtils
            .convertDateTimeFromServer(json.createTime);
        entity.modifyTime = this.dateUtils
            .convertDateTimeFromServer(json.modifyTime);
        return entity;
    }

    /**
     * Convert a Comment to a JSON which can be sent to the server.
     */
    private convert(comment: Comment): Comment {
        const copy: Comment = Object.assign({}, comment);

        copy.createTime = this.dateUtils.toDate(comment.createTime);

        copy.modifyTime = this.dateUtils.toDate(comment.modifyTime);
        return copy;
    }
}
