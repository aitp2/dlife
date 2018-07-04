package com.aitp.dlife.web.rest;

import com.aitp.dlife.web.rest.util.DateUtil;
import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.EvaluateService;
import com.aitp.dlife.service.ImageService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.EvaluateDTO;
import com.aitp.dlife.service.dto.ImageDTO;

import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Evaluate.
 */
@RestController
@RequestMapping("/api")
public class EvaluateResource {

    private final Logger log = LoggerFactory.getLogger(EvaluateResource.class);

    private static final String ENTITY_NAME = "evaluate";

    private final EvaluateService evaluateService;

    private final ImageService imageService;

    public EvaluateResource(EvaluateService evaluateService,ImageService imageService) {
        this.evaluateService = evaluateService;
        this.imageService =  imageService;
    }

    /**
     * POST  /evaluates : Create a new evaluate.
     *
     * @param evaluateDTO the evaluateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evaluateDTO, or with status 400 (Bad Request) if the evaluate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/evaluates")
    @Timed
    public ResponseEntity<EvaluateDTO> createEvaluate(@Valid @RequestBody EvaluateDTO evaluateDTO) throws URISyntaxException {
        log.debug("REST request to save Evaluate : {}", evaluateDTO);
        if (evaluateDTO.getId() != null) {
            throw new BadRequestAlertException("A new evaluate cannot already have an ID", ENTITY_NAME, "idexists");
        }

        //set default messages
        evaluateDTO.setCreateTime(DateUtil.getYMDDateString(new Date()));
        evaluateDTO.setModifyTime(DateUtil.getYMDDateString(new Date()));

        EvaluateDTO result = evaluateService.save(evaluateDTO);

        if (!CollectionUtils.isEmpty(evaluateDTO.getImages()))
        {
            for(ImageDTO imageDTO : evaluateDTO.getImages())
            {
                if (imageDTO != null && StringUtils.isNotEmpty(imageDTO.getOssPath()))
                {
                    imageDTO.setEvaluatId(evaluateDTO.getId());
                    imageDTO.setCreateTime(DateUtil.getYMDDateString(new Date()));
                    imageService.save(imageDTO);
                }
            }
        }

        return ResponseEntity.created(new URI("/api/evaluates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /evaluates : Updates an existing evaluate.
     *
     * @param evaluateDTO the evaluateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evaluateDTO,
     * or with status 400 (Bad Request) if the evaluateDTO is not valid,
     * or with status 500 (Internal Server Error) if the evaluateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/evaluates")
    @Timed
    public ResponseEntity<EvaluateDTO> updateEvaluate(@Valid @RequestBody EvaluateDTO evaluateDTO) throws URISyntaxException {
        log.debug("REST request to update Evaluate : {}", evaluateDTO);
        if (evaluateDTO.getId() == null) {
            return createEvaluate(evaluateDTO);
        }

        //set default messages
        evaluateDTO.setModifyTime(DateUtil.getYMDDateString(new Date()));

        //we need to compare the old image with the new image,
        List<ImageDTO> oldImages = imageService.findImagesByEvaluatId(evaluateDTO.getId());
        if (!CollectionUtils.isEmpty(evaluateDTO.getImages()))
        {

            List<Long> oldIds = new ArrayList<>();

            for(ImageDTO newImage : evaluateDTO.getImages())
            {
                if(newImage.getId() == null)
                {
                    newImage.setCreateTime(DateUtil.getYMDDateString(new Date()));
                    newImage.setEvaluatId(evaluateDTO.getId());
                    imageService.save(newImage);
                    continue;
                }

                oldIds.add(newImage.getId());
            }

            for(ImageDTO oldImage : oldImages)
            {
                if (!oldIds.contains(oldImage.getId()))
                {
                    imageService.delete(oldImage.getId());
                }
            }
        }
        else
        {
            for(ImageDTO oldImage : oldImages)
            {
                imageService.delete(oldImage.getId());
            }
        }

        EvaluateDTO result = evaluateService.save(evaluateDTO);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, evaluateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /evaluates : get all the evaluates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of evaluates in body
     */
    @GetMapping("/evaluates")
    @Timed
    public ResponseEntity<List<EvaluateDTO>> getAllEvaluates(Pageable pageable) {
        log.debug("REST request to get a page of Evaluates");
        Page<EvaluateDTO> page = evaluateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/evaluates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /evaluates/:id : get the "id" evaluate.
     *
     * @param id the id of the evaluateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evaluateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/evaluates/{id}")
    @Timed
    public ResponseEntity<EvaluateDTO> getEvaluate(@PathVariable Long id) {
        log.debug("REST request to get Evaluate : {}", id);
        EvaluateDTO evaluateDTO = evaluateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(evaluateDTO));
    }

    /**
     * DELETE  /evaluates/:id : delete the "id" evaluate.
     *
     * @param id the id of the evaluateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/evaluates/{id}")
    @Timed
    public ResponseEntity<Void> deleteEvaluate(@PathVariable Long id) {
        log.debug("REST request to delete Evaluate : {}", id);
        evaluateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
