package com.aitp.dlife.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * 评价信息
 */
@ApiModel(description = "评价信息")
@Entity
@Table(name = "evaluate")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class Evaluate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Size(max = 500)
    @Column(name = "content", length = 500)
    private String content;

    @Column(name = "taste_score")
    private Integer tasteScore;

    @Column(name = "service_score")
    private Integer serviceScore;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "modify_time")
    private Instant modifyTime;

    @OneToMany(mappedBy = "evaluat")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<Image> images = new HashSet<>();

    @ManyToOne
    private RecipeOrder recipeOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public Evaluate parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public Evaluate content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTasteScore() {
        return tasteScore;
    }

    public Evaluate tasteScore(Integer tasteScore) {
        this.tasteScore = tasteScore;
        return this;
    }

    public void setTasteScore(Integer tasteScore) {
        this.tasteScore = tasteScore;
    }

    public Integer getServiceScore() {
        return serviceScore;
    }

    public Evaluate serviceScore(Integer serviceScore) {
        this.serviceScore = serviceScore;
        return this;
    }

    public void setServiceScore(Integer serviceScore) {
        this.serviceScore = serviceScore;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Evaluate createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public Evaluate modifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Set<Image> getImages() {
        return images;
    }

    public Evaluate images(Set<Image> images) {
        this.images = images;
        return this;
    }

    public Evaluate addImage(Image image) {
        this.images.add(image);
        image.setEvaluat(this);
        return this;
    }

    public Evaluate removeImage(Image image) {
        this.images.remove(image);
        image.setEvaluat(null);
        return this;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public RecipeOrder getRecipeOrder() {
        return recipeOrder;
    }

    public Evaluate recipeOrder(RecipeOrder recipeOrder) {
        this.recipeOrder = recipeOrder;
        return this;
    }

    public void setRecipeOrder(RecipeOrder recipeOrder) {
        this.recipeOrder = recipeOrder;
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
        Evaluate evaluate = (Evaluate) o;
        if (evaluate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), evaluate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Evaluate{" +
            "id=" + getId() +
            ", parentId=" + getParentId() +
            ", content='" + getContent() + "'" +
            ", tasteScore=" + getTasteScore() +
            ", serviceScore=" + getServiceScore() +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
