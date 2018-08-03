package com.aitp.dlife.web.rest;

import com.aitp.dlife.service.dto.*;
import com.aitp.dlife.web.rest.util.DateUtil;
import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.RecipeOrderService;
import com.aitp.dlife.service.RecipeService;
import com.aitp.dlife.service.WechatUserService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Recipe.
 */
@RestController
@RequestMapping("/api")
public class RecipeResource {

    private final Logger log = LoggerFactory.getLogger(RecipeResource.class);

    private static final String ENTITY_NAME = "recipe";

    private final RecipeService recipeService;


    private final WechatUserService wechatUserService;

    private final RecipeOrderService recipeOrderService;


    public RecipeResource(RecipeService recipeService,WechatUserService wechatUserService,
    		RecipeOrderService recipeOrderService) {
        this.recipeService = recipeService;
        this.wechatUserService = wechatUserService;
        this.recipeOrderService  = recipeOrderService;
    }

    /**
     * POST  /recipes : Create a new recipe.
     *
     * @param recipeDTO the recipeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recipeDTO, or with status 400 (Bad Request) if the recipe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recipes")
    @Timed
    public ResponseEntity<RecipeDTO> createRecipe(@Valid @RequestBody RecipeDTO recipeDTO) throws URISyntaxException {
        log.debug("REST request to save Recipe : {}", recipeDTO);
        if (recipeDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipe cannot already have an ID", ENTITY_NAME, "idexists");
        }

        // set the wechat user message
        if (recipeDTO.getWechatUserId() == null)
        {
            throw new BadRequestAlertException("The request must have the weChatUserId", ENTITY_NAME, "noWeChatUserId");
        }
        try {
            Long.valueOf(recipeDTO.getWechatUserId());
        } catch (NumberFormatException e) {
            throw new BadRequestAlertException("The request wechat user id must be number type", ENTITY_NAME, "notNumberType");
        }
        WechatUserDTO wechatUserDTO = wechatUserService.findOne(Long.valueOf(recipeDTO.getWechatUserId()));
        if (wechatUserDTO == null)
        {
            throw new BadRequestAlertException("Can not get the weChatUser by id:" + recipeDTO.getWechatUserId(), ENTITY_NAME, "noWeChatUser");
        }
//        else if(BooleanUtils.isNotTrue(wechatUserDTO.isCookFlag()))
//        {
//            throw new BadRequestAlertException("Have no permission to create recipes for WeChatUser id:" + recipeDTO.getWechatUserId(), ENTITY_NAME, "noPermissionWeChatUser");
//        }
        else
        {
            recipeDTO.setAvatar(wechatUserDTO.getAvatar());
            recipeDTO.setNickName(wechatUserDTO.getNickName());
        }

        // set other default message
        recipeDTO.setCreateTime(DateUtil.getYMDDateString(new Date()));
        recipeDTO.setModifyTime(DateUtil.getYMDDateString(new Date()));
        recipeDTO.setHot(Integer.valueOf(0));
        if (recipeDTO.getPrice() == null)
        {
            recipeDTO.setPrice(Double.valueOf(0));
        }
        recipeDTO.setPublishVersion(Integer.valueOf(1));

        RecipeDTO result = recipeService.save(recipeDTO);

        if (!CollectionUtils.isEmpty(recipeDTO.getImages()))
        {
            for(ImageDTO imageDTO : recipeDTO.getImages())
            {
                if (imageDTO != null && StringUtils.isNotEmpty(imageDTO.getOssPath()))
                {
                    imageDTO.setRecipeId(result.getId());
                    imageDTO.setCreateTime(DateUtil.getYMDDateString(new Date()));
//                    imageService.save(imageDTO);
                }
            }
        }
        return ResponseEntity.created(new URI("/api/recipes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recipes : Updates an existing recipe.
     *
     * @param recipeDTO the recipeDTO to update
     * @param newVersion is the recipe need to build a new version
     * @return the ResponseEntity with status 200 (OK) and with body the updated recipeDTO,
     * or with status 400 (Bad Request) if the recipeDTO is not valid,
     * or with status 500 (Internal Server Error) if the recipeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recipes")
    @Timed
    public ResponseEntity<RecipeDTO> updateRecipe(@Valid @RequestBody RecipeDTO recipeDTO,
            @RequestParam(value = "newVersion", required = false) Boolean newVersion) throws URISyntaxException {
        log.debug("REST request to update Recipe : {}", recipeDTO);
        if (recipeDTO.getId() == null) {
            return createRecipe(recipeDTO);
        }

        //set default messages
        recipeDTO.setModifyTime(DateUtil.getYMDDateString(new Date()));

        // if new version is true, we will increase the recipe version
        if (BooleanUtils.isTrue(newVersion))
        {
            RecipeDTO oldRecipeDTO = recipeService.findOne(recipeDTO.getId());
            if (oldRecipeDTO == null)
            {
                throw new BadRequestAlertException("Can not get the recipe by id:" + recipeDTO.getId(), ENTITY_NAME, "noRecipe");
            }
            else if(oldRecipeDTO.getPublishVersion() == null)
            {
                recipeDTO.setPublishVersion(Integer.valueOf(1));
            }
            else
            {
                recipeDTO.setPublishVersion(Integer.valueOf(oldRecipeDTO.getPublishVersion().intValue() + 1));
            }
        }

        //TODO we need to consider that if the recipe have been ordered, and then the cooker change the version.

        //we need to compare the old image with the new image,
//        List<ImageDTO> oldImages = imageService.findImagesByRecipeId(recipeDTO.getId());
//        if (!CollectionUtils.isEmpty(recipeDTO.getImages()))
//        {
//
//            List<Long> oldIds = new ArrayList<>();
//
//            for(ImageDTO newImage : recipeDTO.getImages())
//            {
//                if(newImage.getId() == null)
//                {
//                    newImage.setCreateTime(DateUtil.getYMDDateString(new Date()));
//                    newImage.setRecipeId(recipeDTO.getId());
//                    imageService.save(newImage);
//                    continue;
//                }
//
//                oldIds.add(newImage.getId());
//            }
//
//            for(ImageDTO oldImage : oldImages)
//            {
//                if (!oldIds.contains(oldImage.getId()))
//                {
//                    imageService.delete(oldImage.getId());
//                }
//            }
//        }
//        else
//        {
//            for(ImageDTO oldImage : oldImages)
//            {
//                imageService.delete(oldImage.getId());
//            }
//        }

        RecipeDTO result = recipeService.save(recipeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recipeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recipes : get the cook's recipes.
     *
     * @param pageable the pagination information
     * @param id the cook's wechatUserId
     * @return the ResponseEntity with status 200 (OK) and the list of recipes in body
     */
    @GetMapping("/cook-recipes/{id}")
    @Timed
    public ResponseEntity<List<RecipeDTO>> getCookRecipes(Pageable pageable,@PathVariable String id) {
        log.debug("REST request to get a page of Recipes");
        Page<RecipeDTO> page = null;
        if(!StringUtils.isEmpty(id)) {
        	page = recipeService.findAllByWechatUserId(pageable, id);
        }
        else
        {
            throw new BadRequestAlertException("The request must have id (the weChatUserId)", ENTITY_NAME, "noId");
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/recipes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /recipes : get all the recipes.
     *
     * @param pageable the pagination information
     * @param wechatUserId the currentUserId
     * @return the ResponseEntity with status 200 (OK) and the list of recipes in body
     */
    @GetMapping("/recipes")
    @Timed
    public ResponseEntity<List<RecipeDTO>> getAllRecipes(Pageable pageable,
             @RequestParam(value = "wechatUserId", required = true) String wechatUserId) {
        log.debug("REST request to get a page of Recipes");
        Page<RecipeDTO> page = null;
        page = recipeService.findAll(pageable, wechatUserId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/recipes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /recipes/:id : get the "id" recipe.
     *
     * @param id the id of the recipeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recipeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recipes/{id}")
    @Timed
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable Long id) {
        log.debug("REST request to get Recipe : {}", id);
        RecipeDTO recipeDTO = recipeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(recipeDTO));
    }

    /**
     * GET  /recipes/:recipeId : get the "recipeId" recipe.
     *
     * @param recipeId the id of the recipeDTO to retrieve
     * @param wechatUserId the wechatUserId of the recipeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recipeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recipedetails/{recipeId}")
    @Timed
    public ResponseEntity<RecipeDetailDTO> getRecipeDetail(@PathVariable Long recipeId,
                                                           @RequestParam(value = "wechatUserId", required = true) String wechatUserId) {

        log.debug("REST request to get RecipeDetailDTO : {}", recipeId);
        RecipeDetailDTO recipeDetailDTO = new RecipeDetailDTO();
        RecipeDTO recipeDTO = recipeService.findOne(recipeId,wechatUserId);

//        if (recipeDTO != null)
//        {
//            recipeDTO.setHot(Integer.valueOf(recipeDTO.getHot() == null ? 1 : recipeDTO.getHot().intValue() + 1));
//            recipeService.save(recipeDTO);
//        }

        recipeDetailDTO.setRecipeDTO(recipeDTO);

        //user
        try {
            Long.valueOf(recipeDTO.getWechatUserId());
        } catch (NumberFormatException e) {
            throw new BadRequestAlertException("The request wechat user id must be number type", ENTITY_NAME, "notNumberType");
        }

        WechatUserDTO wechatUserDTO = wechatUserService.findOne(Long.valueOf(recipeDTO.getWechatUserId()));
        recipeDetailDTO.setWechatUserDTO(wechatUserDTO);
        //orders
        List<RecipeOrderDTO>  list_RecipeOrderDTO = recipeOrderService.findAllByRecipeId(recipeDTO.getId());
        recipeDetailDTO.setRecipeOrderDTOList(list_RecipeOrderDTO);
        recipeDetailDTO.setRecipeOrderSize(new Long(list_RecipeOrderDTO.size()));
        //evaluate
        List<EvaluateDTO> list_evaluateDTO = new ArrayList<EvaluateDTO> ();
        for(RecipeOrderDTO recipeOrderDTO:list_RecipeOrderDTO) {
//        	list_evaluateDTO.addAll(evaluateService.findAllByRecipeOrderId(recipeOrderDTO.getId()));
        }
        recipeDetailDTO.setEvaluateDTOList(list_evaluateDTO);
        //likeRecipe
        int pageNumber = 1;
        int pageSize = 2;
        String descOrAsc="desc";
        String sortFiled="startTime";
        //生成Sort变量
        Sort sort = new Sort(Direction.fromString(descOrAsc), sortFiled);
        Pageable pageable = new PageRequest(pageNumber - 1, pageSize, sort);
        Page<RecipeDTO> page = recipeService.findAll(pageable);
        recipeDetailDTO.setLikeRecipeDTOList(page.getContent());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(recipeDetailDTO));
    }

    /**
     * DELETE  /recipes/:id : delete the "id" recipe.
     *
     * @param id the id of the recipeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recipes/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        log.debug("REST request to delete Recipe : {}", id);
        recipeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
