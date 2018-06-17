import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Image } from './image.model';
import { ImageService } from './image.service';

@Component({
    selector: 'jhi-image-detail',
    templateUrl: './image-detail.component.html'
})
export class ImageDetailComponent implements OnInit, OnDestroy {

    image: Image;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private imageService: ImageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInImages();
    }

    load(id) {
        this.imageService.find(id)
            .subscribe((imageResponse: HttpResponse<Image>) => {
                this.image = imageResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInImages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'imageListModification',
            (response) => this.load(this.image.id)
        );
    }
}
