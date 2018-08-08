package com.aitp.dlife.service.dto;

import java.io.Serializable;
import java.util.List;

public class RecipeDetailDTO implements Serializable{

	private RecipeDTO recipeDTO;
	
	private WechatUserDTO wechatUserDTO;
	
	private List<RecipeOrderDTO> recipeOrderDTOList;
	
	private Long recipeOrderSize;
	
	private List<EvaluateDTO> evaluateDTOList;
	
	private List<RecipeDTO> likeRecipeDTOList;

	public Long getRecipeOrderSize() {
		return recipeOrderSize;
	}

	public void setRecipeOrderSize(Long recipeOrderSize) {
		this.recipeOrderSize = recipeOrderSize;
	}

	public RecipeDTO getRecipeDTO() {
		return recipeDTO;
	}

	public void setRecipeDTO(RecipeDTO recipeDTO) {
		this.recipeDTO = recipeDTO;
	}

	public WechatUserDTO getWechatUserDTO() {
		return wechatUserDTO;
	}

	public void setWechatUserDTO(WechatUserDTO wechatUserDTO) {
		this.wechatUserDTO = wechatUserDTO;
	}

	public List<RecipeOrderDTO> getRecipeOrderDTOList() {
		return recipeOrderDTOList;
	}

	public void setRecipeOrderDTOList(List<RecipeOrderDTO> recipeOrderDTOList) {
		this.recipeOrderDTOList = recipeOrderDTOList;
	}

	public List<EvaluateDTO> getEvaluateDTOList() {
		return evaluateDTOList;
	}

	public void setEvaluateDTOList(List<EvaluateDTO> evaluateDTOList) {
		this.evaluateDTOList = evaluateDTOList;
	}

	public List<RecipeDTO> getLikeRecipeDTOList() {
		return likeRecipeDTOList;
	}

	public void setLikeRecipeDTOList(List<RecipeDTO> likeRecipeDTOList) {
		this.likeRecipeDTOList = likeRecipeDTOList;
	}
}
