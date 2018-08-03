package com.aitp.dlife.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 菜谱订单
 */
@ApiModel(description = "菜谱订单")
@Entity
@Table(name = "recipe_order")
public class RecipeOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 128)
    @Column(name = "wechat_user_id", length = 128)
    private String wechatUserId;

    /**
     * 头像
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "头像")
    @Column(name = "avatar", length = 1024)
    private String avatar;

    /**
     * 昵称
     */
    @Size(max = 128)
    @ApiModelProperty(value = "昵称")
    @Column(name = "nick_name", length = 128)
    private String nickName;

    @Max(value = 9)
    @Column(name = "recipe_version")
    private Integer recipeVersion;

    @Column(name = "price")
    private Double price;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "modify_time")
    private Instant modifyTime;

    @ManyToOne
    @JsonIgnoreProperties("recipeOrders")
    private Recipe recipe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public RecipeOrder wechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getAvatar() {
        return avatar;
    }

    public RecipeOrder avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public RecipeOrder nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getRecipeVersion() {
        return recipeVersion;
    }

    public RecipeOrder recipeVersion(Integer recipeVersion) {
        this.recipeVersion = recipeVersion;
        return this;
    }

    public void setRecipeVersion(Integer recipeVersion) {
        this.recipeVersion = recipeVersion;
    }

    public Double getPrice() {
        return price;
    }

    public RecipeOrder price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public RecipeOrder createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public RecipeOrder modifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public RecipeOrder recipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecipeOrder recipeOrder = (RecipeOrder) o;
        if (recipeOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipeOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecipeOrder{" +
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
