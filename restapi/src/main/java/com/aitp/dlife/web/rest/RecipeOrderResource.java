package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.RecipeOrderService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.RecipeOrderDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RecipeOrder.
 */
@RestController
@RequestMapping("/api")
public class RecipeOrderResource {

    private final Logger log = LoggerFactory.getLogger(RecipeOrderResource.class);

    private static final String ENTITY_NAME = "recipeOrder";

    private final RecipeOrderService recipeOrderService;

    public RecipeOrderResource(RecipeOrderService recipeOrderService) {
        this.recipeOrderService = recipeOrderService;
    }

    /**
     * POST  /recipe-orders : Create a new recipeOrder.
     *
     * @param recipeOrderDTO the recipeOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recipeOrderDTO, or with status 400 (Bad Request) if the recipeOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recipe-orders")
    @Timed
    public ResponseEntity<RecipeOrderDTO> createRecipeOrder(@Valid @RequestBody RecipeOrderDTO recipeOrderDTO) throws URISyntaxException {
        log.debug("REST request to save RecipeOrder : {}", recipeOrderDTO);
        if (recipeOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipeOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecipeOrderDTO result = recipeOrderService.save(recipeOrderDTO);
        return ResponseEntity.created(new URI("/api/recipe-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recipe-orders : Updates an existing recipeOrder.
     *
     * @param recipeOrderDTO the recipeOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recipeOrderDTO,
     * or with status 400 (Bad Request) if the recipeOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the recipeOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recipe-orders")
    @Timed
    public ResponseEntity<RecipeOrderDTO> updateRecipeOrder(@Valid @RequestBody RecipeOrderDTO recipeOrderDTO) throws URISyntaxException {
        log.debug("REST request to update RecipeOrder : {}", recipeOrderDTO);
        if (recipeOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecipeOrderDTO result = recipeOrderService.save(recipeOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recipeOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recipe-orders : get all the recipeOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of recipeOrders in body
     */
    @GetMapping("/recipe-orders")
    @Timed
    public ResponseEntity<List<RecipeOrderDTO>> getAllRecipeOrders(Pageable pageable) {
        log.debug("REST request to get a page of RecipeOrders");
        Page<RecipeOrderDTO> page = recipeOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/recipe-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /recipe-orders/:id : get the "id" recipeOrder.
     *
     * @param id the id of the recipeOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recipeOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recipe-orders/{id}")
    @Timed
    public ResponseEntity<RecipeOrderDTO> getRecipeOrder(@PathVariable Long id) {
        log.debug("REST request to get RecipeOrder : {}", id);
        Optional<RecipeOrderDTO> recipeOrderDTO = recipeOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipeOrderDTO);
    }

    /**
     * DELETE  /recipe-orders/:id : delete the "id" recipeOrder.
     *
     * @param id the id of the recipeOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recipe-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecipeOrder(@PathVariable Long id) {
        log.debug("REST request to delete RecipeOrder : {}", id);
        recipeOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
