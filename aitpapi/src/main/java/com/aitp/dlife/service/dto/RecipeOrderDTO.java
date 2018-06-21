package com.aitp.dlife.service.dto;


import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * A DTO for the RecipeOrder entity.
 */
public class RecipeOrderDTO implements Serializable {

    private Long id;

    @Size(max = 128)
    private String wechatUserId;

    @Size(max = 1024)
    private String avatar;

    @Size(max = 128)
    private String nickName;

    @Max(value = 9)
    private Integer recipeVersion;

    private Double price;

    private String createTime;

    private String modifyTime;

    private Long recipeId;

    private String imageURL;

    private String recipeTile;

    private String recipeStartTime;

    public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getRecipeTile() {
		return recipeTile;
	}

	public void setRecipeTile(String recipeTile) {
		this.recipeTile = recipeTile;
	}

	public String getRecipeStartTime() {
		return recipeStartTime;
	}

	public void setRecipeStartTime(String recipeStartTime) {
		this.recipeStartTime = recipeStartTime;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getRecipeVersion() {
        return recipeVersion;
    }

    public void setRecipeVersion(Integer recipeVersion) {
        this.recipeVersion = recipeVersion;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecipeOrderDTO recipeOrderDTO = (RecipeOrderDTO) o;
        if(recipeOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipeOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecipeOrderDTO{" +
            "id=" + getId() +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", recipeVersion=" + getRecipeVersion() +
            ", price=" + getPrice() +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
