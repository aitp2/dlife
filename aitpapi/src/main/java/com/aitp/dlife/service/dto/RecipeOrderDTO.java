package com.aitp.dlife.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the RecipeOrder entity.
 */
public class RecipeOrderDTO implements Serializable {

    private Long id;

    @Size(max = 128)
    private String wechatUserId;

    @Max(value = 9)
    private Integer recipeVersion;

    private Double price;

    private Instant createTime;

    private Instant modifyTime;

    private Long recipeId;

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

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Instant modifyTime) {
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
            ", recipeVersion=" + getRecipeVersion() +
            ", price=" + getPrice() +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
