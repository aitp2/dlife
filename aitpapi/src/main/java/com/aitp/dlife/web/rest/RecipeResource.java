package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.EvaluateService;
import com.aitp.dlife.service.ImageService;
import com.aitp.dlife.service.RecipeOrderService;
import com.aitp.dlife.service.RecipeService;
import com.aitp.dlife.service.WechatUserService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.EvaluateDTO;
import com.aitp.dlife.service.dto.ImageDTO;
import com.aitp.dlife.service.dto.RecipeDTO;
import com.aitp.dlife.service.dto.RecipeDetailDTO;
import com.aitp.dlife.service.dto.RecipeOrderDTO;
import com.aitp.dlife.service.dto.WechatUserDTO;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
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
    
    private final ImageService imageService;
    
    private final WechatUserService wechatUserService;
    
    private final RecipeOrderService recipeOrderService;
    
    private final EvaluateService evaluateService;

    public RecipeResource(RecipeService recipeService,ImageService imageService,WechatUserService wechatUserService,
    		RecipeOrderService recipeOrderService,EvaluateService evaluateService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
        this.wechatUserService = wechatUserService;
        this.recipeOrderService  = recipeOrderService;
        this.evaluateService = evaluateService;
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
        RecipeDTO result = recipeService.save(recipeDTO);
        //save image
        List<String> imagePathList = recipeDTO.getListImageURL();
        for(String path : imagePathList) {
        	ImageDTO image = new ImageDTO();
        	image.setOssPath(path);
        	image.setRecipeId(result.getId());
        	image.setCreateTime(Instant.now());
        	imageService.save(image);
        }
        return ResponseEntity.created(new URI("/api/recipes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recipes : Updates an existing recipe.
     *
     * @param recipeDTO the recipeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recipeDTO,
     * or with status 400 (Bad Request) if the recipeDTO is not valid,
     * or with status 500 (Internal Server Error) if the recipeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recipes")
    @Timed
    public ResponseEntity<RecipeDTO> updateRecipe(@Valid @RequestBody RecipeDTO recipeDTO) throws URISyntaxException {
        log.debug("REST request to update Recipe : {}", recipeDTO);
        if (recipeDTO.getId() == null) {
            return createRecipe(recipeDTO);
        }
      //TODO recipeVersion 更新
        RecipeDTO result = recipeService.save(recipeDTO);
      //save image
        List<String> imagePathList = recipeDTO.getListImageURL();
        for(String path : imagePathList) {
        	ImageDTO image = new ImageDTO();
        	image.setOssPath(path);
        	image.setRecipeId(result.getId());
        	image.setCreateTime(Instant.now());
        	imageService.save(image);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recipeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recipes : get all the recipes.
     *
     * @param pageable the pagination information
     * @param wechatUserId the wechatUserId
     * @return the ResponseEntity with status 200 (OK) and the list of recipes in body
     */
    @GetMapping("/recipes")
    @Timed
    public ResponseEntity<List<RecipeDTO>> getAllRecipes(Pageable pageable,
    		@RequestParam(value = "wechatUserId", required = false) String wechatUserId) {
        log.debug("REST request to get a page of Recipes");
        Page<RecipeDTO> page = null;
        if(!StringUtils.isEmpty(wechatUserId)) {
        	page = recipeService.findAllByWechatUserId(pageable, wechatUserId);
        }else {
        	page = recipeService.findAll(pageable);
        }
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
     * GET  /recipes/:id : get the "id" recipe.
     *
     * @param id the id of the recipeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recipeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recipedetails/{id}")
    @Timed
    public ResponseEntity<RecipeDetailDTO> getRecipeDetail(@PathVariable Long id) {
        log.debug("REST request to get RecipeDetailDTO : {}", id);
        RecipeDetailDTO recipeDetailDTO = new RecipeDetailDTO();
        RecipeDTO recipeDTO = recipeService.findOne(id);
        recipeDetailDTO.setRecipeDTO(recipeDTO);
        //use
        WechatUserDTO wechatUserDTO = wechatUserService.findByOpenId(recipeDTO.getWechatUserId());
        recipeDetailDTO.setWechatUserDTO(wechatUserDTO);
        //orders
        List<RecipeOrderDTO>  list_RecipeOrderDTO = recipeOrderService.findAllByRecipeId(recipeDTO.getId());
        recipeDetailDTO.setRecipeOrderDTOList(list_RecipeOrderDTO);
        recipeDetailDTO.setRecipeOrderSize(new Long(list_RecipeOrderDTO.size()));
        //evaluate
        List<EvaluateDTO> list_evaluateDTO = new ArrayList<EvaluateDTO> ();
        for(RecipeOrderDTO recipeOrderDTO:list_RecipeOrderDTO) {
        	list_evaluateDTO.addAll(evaluateService.findAllByRecipeOrderId(recipeOrderDTO.getId()));
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
