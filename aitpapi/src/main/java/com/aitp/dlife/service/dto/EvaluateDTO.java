package com.aitp.dlife.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Evaluate entity.
 */
public class EvaluateDTO implements Serializable {

    private Long id;

    private Long parentId;

    @Size(max = 500)
    private String content;

    private Integer tasteScore;

    private Integer serviceScore;

    private Instant createTime;

    private Instant modifyTime;

    private Long recipeOrderId;
    
    private List<String> listImageURL;

    public List<String> getListImageURL() {
		return listImageURL;
	}

	public void setListImageURL(List<String> listImageURL) {
		this.listImageURL = listImageURL;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTasteScore() {
        return tasteScore;
    }

    public void setTasteScore(Integer tasteScore) {
        this.tasteScore = tasteScore;
    }

    public Integer getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(Integer serviceScore) {
        this.serviceScore = serviceScore;
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

    public Long getRecipeOrderId() {
        return recipeOrderId;
    }

    public void setRecipeOrderId(Long recipeOrderId) {
        this.recipeOrderId = recipeOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EvaluateDTO evaluateDTO = (EvaluateDTO) o;
        if(evaluateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), evaluateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EvaluateDTO{" +
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
