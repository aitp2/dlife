package com.aitp.dlife.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * 菜谱信息
 */
@ApiModel(description = "菜谱信息")
@Entity
@Table(name = "recipe")
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 发布者id
     */
    @Size(max = 128)
    @ApiModelProperty(value = "发布者id")
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

    @Size(max = 255)
    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "price")
    private Double price;

    @Max(value = 9)
    @Column(name = "num")
    private Integer num;

    @Max(value = 2)
    @Column(name = "status")
    private Integer status;

    @Max(value = 9)
    @Column(name = "publish_version")
    private Integer publishVersion;

    @Max(value = 9)
    @Column(name = "hot")
    private Integer hot;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "modify_time")
    private Instant modifyTime;

    @OneToMany(mappedBy = "recipe")
    @JsonIgnore
    private Set<RecipeOrder> recipeOrders = new HashSet<>();

    @OneToMany(mappedBy = "recipe")
    @JsonIgnore
    private Set<Image> images = new HashSet<>();

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

    public Recipe wechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getAvatar() {
        return avatar;
    }

    public Recipe avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public Recipe nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTitle() {
        return title;
    }

    public Recipe title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public Recipe content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Recipe startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public Recipe endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Double getPrice() {
        return price;
    }

    public Recipe price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public Recipe num(Integer num) {
        this.num = num;
        return this;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStatus() {
        return status;
    }

    public Recipe status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPublishVersion() {
        return publishVersion;
    }

    public Recipe publishVersion(Integer publishVersion) {
        this.publishVersion = publishVersion;
        return this;
    }

    public void setPublishVersion(Integer publishVersion) {
        this.publishVersion = publishVersion;
    }

    public Integer getHot() {
        return hot;
    }

    public Recipe hot(Integer hot) {
        this.hot = hot;
        return this;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Recipe createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public Recipe modifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Set<RecipeOrder> getRecipeOrders() {
        return recipeOrders;
    }

    public Recipe recipeOrders(Set<RecipeOrder> recipeOrders) {
        this.recipeOrders = recipeOrders;
        return this;
    }

    public Recipe addRecipeOrder(RecipeOrder recipeOrder) {
        this.recipeOrders.add(recipeOrder);
        recipeOrder.setRecipe(this);
        return this;
    }

    public Recipe removeRecipeOrder(RecipeOrder recipeOrder) {
        this.recipeOrders.remove(recipeOrder);
        recipeOrder.setRecipe(null);
        return this;
    }

    public void setRecipeOrders(Set<RecipeOrder> recipeOrders) {
        this.recipeOrders = recipeOrders;
    }

    public Set<Image> getImages() {
        return images;
    }

    public Recipe images(Set<Image> images) {
        this.images = images;
        return this;
    }

    public Recipe addImage(Image image) {
        this.images.add(image);
        image.setRecipe(this);
        return this;
    }

    public Recipe removeImage(Image image) {
        this.images.remove(image);
        image.setRecipe(null);
        return this;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
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
        Recipe recipe = (Recipe) o;
        if (recipe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Recipe{" +
            "id=" + getId() +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", price=" + getPrice() +
            ", num=" + getNum() +
            ", status=" + getStatus() +
            ", publishVersion=" + getPublishVersion() +
            ", hot=" + getHot() +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
