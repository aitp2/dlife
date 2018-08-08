package com.aitp.dlife.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.aitp.dlife.service.RecipeService;
import com.aitp.dlife.service.WechatUserService;
import com.aitp.dlife.service.dto.RecipeDTO;
import com.aitp.dlife.service.dto.WechatUserDTO;
import com.aitp.dlife.web.rest.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aitp.dlife.service.RecipeOrderService;
import com.aitp.dlife.service.RecipeService;
import com.aitp.dlife.service.dto.RecipeDTO;
import com.aitp.dlife.service.dto.RecipeOrderDTO;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing RecipeOrder.
 */
@RestController
@RequestMapping("/api")
public class RecipeOrderResource {

    private final Logger log = LoggerFactory.getLogger(RecipeOrderResource.class);

    private static final String ENTITY_NAME = "recipeOrder";

    private final RecipeOrderService recipeOrderService;

    private final RecipeService recipeService;

    private final WechatUserService wechatUserService;

    public RecipeOrderResource(RecipeOrderService recipeOrderService, RecipeService recipeService, WechatUserService wechatUserService) {
        this.recipeOrderService = recipeOrderService;
        this.recipeService = recipeService;
        this.wechatUserService = wechatUserService;
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

        //set default messages
        recipeOrderDTO.setCreateTime(DateUtil.getYMDDateString(new Date()));
        recipeOrderDTO.setModifyTime(DateUtil.getYMDDateString(new Date()));

        //set Wechat user message
        if (StringUtils.isEmpty(recipeOrderDTO.getWechatUserId()))
        {
            throw new BadRequestAlertException("The request must have the wechatUserId", ENTITY_NAME, "noWechatUserId");
        }
        try {
            Long.valueOf(recipeOrderDTO.getWechatUserId());
        } catch (NumberFormatException e) {
            throw new BadRequestAlertException("The request wechat user id must be number type", ENTITY_NAME, "notNumberType");
        }
        WechatUserDTO wechatUserDTO = wechatUserService.findOne(Long.valueOf(recipeOrderDTO.getWechatUserId()));
        if (wechatUserDTO == null)
        {
            throw new BadRequestAlertException("Can not get the wehcatUser by user id:" + recipeOrderDTO.getWechatUserId(), ENTITY_NAME, "noFollowUser");
        }
        else
        {
            recipeOrderDTO.setAvatar(wechatUserDTO.getAvatar());
            recipeOrderDTO.setNickName(wechatUserDTO.getNickName());
        }

        //set recipe message
        if (recipeOrderDTO.getRecipeId() == null)
        {
            throw new BadRequestAlertException("The request must have the recipeId", ENTITY_NAME, "noRecipeId");
        }
        RecipeDTO recipe = recipeService.findOne(recipeOrderDTO.getRecipeId());
        if (recipe == null)
        {
            throw new BadRequestAlertException("Can not get the recipe by recipeId:" + recipeOrderDTO.getRecipeId(), ENTITY_NAME, "noRecipe");
        }
        recipeOrderDTO.setPrice(recipe.getPrice());
        if (recipeOrderDTO.getRecipeVersion() == null)
        {
            recipeOrderDTO.setRecipeVersion(recipe.getPublishVersion());
        }
        recipeOrderDTO.setRecipeStartTime(recipe.getStartTime());
        recipeOrderDTO.setRecipeTile(recipe.getTitle());

        //TODO 考虑抢购限量菜品，秒杀排队问题 暂考虑数量限制
        List<RecipeOrderDTO> list_order =recipeOrderService.findAllByRecipeId(recipeOrderDTO.getRecipeId());
        if(recipe.getNum()<=list_order.size()) {
        	throw new BadRequestAlertException("recipe:"+recipe.getId() +"recipe num overflow", ENTITY_NAME, "recipenumoverflow");
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
            return createRecipeOrder(recipeOrderDTO);
        }

        //set default messages
        recipeOrderDTO.setModifyTime(DateUtil.getYMDDateString(new Date()));

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
    public ResponseEntity<List<RecipeOrderDTO>> getAllRecipeOrders(Pageable pageable,
    		@RequestParam(value = "wechatUserId", required = false) String wechatUserId) {
        log.debug("REST request to get a page of RecipeOrders");
        Page<RecipeOrderDTO> page = null;
        if(!StringUtils.isEmpty(wechatUserId)) {
        	page = recipeOrderService.findAllByWechatUserId(pageable, wechatUserId);
        }else {
        	page = recipeOrderService.findAll(pageable);
        }
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
        RecipeOrderDTO recipeOrderDTO = recipeOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(recipeOrderDTO));
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
