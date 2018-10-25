package com.aitp.dlife.service.dto;



import javax.persistence.Column;
import javax.validation.constraints.*;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PinFanActivity entity.
 */
public class PinFanActivityDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    @Size(max = 128)
    private String wechatUserId;

    @Size(max = 1024)
    private String avatar;

    @Size(max = 128)
    private String nickName;

    private Integer activitiyType;

    @Size(max = 128)
    private String activitiyTile;

    private BigDecimal budget;

    @Size(max = 1024)
    private String activitiyAddre;


    private String descrption;

    @Size(max = 128)
    private String organizeUser;

    @Size(max = 128)
    private String coverPicture;

    private String appointDatetime;

    private String appointEndDatetime;

    @Size(max = 1024)
    private String salerUrl;

    private Integer lowerLimit;

    private Integer upperLimit;

    @Size(max = 32)
    private String payType;

    private String deadline;

    private String comment;

    private Integer commentCount;
    
    private Integer readingCount;
    
    private String modifyTime;
    
    private String completedSequence;

    private boolean attended = false;
    @ApiModelProperty(value="报名状态（0-截至，1-开始）")
    private Integer joinStatus = 1;
    
    private Boolean official;
    
    private Boolean stick;

    private String stickOrder;

    private Set<AttendeeDTO> attendees = new HashSet<>();

    private Set<PinfanPicsDTO> pinfanPics = new HashSet<>();

    private Set<RatesDTO> rates = new HashSet<>();
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    @ApiModelProperty(value="活动状态（0-创建，1-完成,2-取消）")
    private Integer status;

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }


    public Set<AttendeeDTO> getAttendees() {
        return attendees;
    }

    public void setAttendees(Set<AttendeeDTO> attendees) {
        this.attendees = attendees;
    }

    public Set<PinfanPicsDTO> getPinfanPics() {
        return pinfanPics;
    }

    public void setPinfanPics(Set<PinfanPicsDTO> pinfanPics) {
        this.pinfanPics = pinfanPics;
    }

    public Set<RatesDTO> getRates() {
        return rates;
    }

    public void setRates(Set<RatesDTO> rates) {
        this.rates = rates;
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

    public Integer getActivitiyType() {
        return activitiyType;
    }

    public void setActivitiyType(Integer activitiyType) {
        this.activitiyType = activitiyType;
    }

    public String getActivitiyTile() {
        return activitiyTile;
    }

    public void setActivitiyTile(String activitiyTile) {
        this.activitiyTile = activitiyTile;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getActivitiyAddre() {
        return activitiyAddre;
    }

    public void setActivitiyAddre(String activitiyAddre) {
        this.activitiyAddre = activitiyAddre;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getOrganizeUser() {
        return organizeUser;
    }

    public void setOrganizeUser(String organizeUser) {
        this.organizeUser = organizeUser;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getAppointDatetime() {
        return appointDatetime;
    }

    public void setAppointDatetime(String appointDatetime) {
        this.appointDatetime = appointDatetime;
    }

    public String getAppointEndDatetime() {
        return appointEndDatetime;
    }

    public void setAppointEndDatetime(String appointEndDatetime) {
        this.appointEndDatetime = appointEndDatetime;
    }

    public String getSalerUrl() {
        return salerUrl;
    }

    public void setSalerUrl(String salerUrl) {
        this.salerUrl = salerUrl;
    }

    public Integer getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Integer lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Integer getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    

    public Integer getReadingCount() {
		return readingCount;
	}

	public void setReadingCount(Integer readingCount) {
		this.readingCount = readingCount;
	}
	

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public String getCompletedSequence() {
		return completedSequence;
	}

	public void setCompletedSequence(String completedSequence) {
		this.completedSequence = completedSequence;
	}
	
	public Integer getJoinStatus() {
		return joinStatus;
	}

	public void setJoinStatus(Integer joinStatus) {
		this.joinStatus = joinStatus;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PinFanActivityDTO pinFanActivityDTO = (PinFanActivityDTO) o;
        if(pinFanActivityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pinFanActivityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
	public String toString() {
		return "PinFanActivityDTO [id=" + id + ", wechatUserId=" + wechatUserId + ", avatar=" + avatar + ", nickName="
				+ nickName + ", activitiyType=" + activitiyType + ", activitiyTile=" + activitiyTile + ", budget="
				+ budget + ", activitiyAddre=" + activitiyAddre + ", descrption=" + descrption + ", organizeUser="
				+ organizeUser + ", coverPicture=" + coverPicture + ", appointDatetime=" + appointDatetime
				+ ", appointEndDatetime=" + appointEndDatetime + ", salerUrl=" + salerUrl + ", lowerLimit=" + lowerLimit
				+ ", upperLimit=" + upperLimit + ", payType=" + payType + ", deadline=" + deadline + ", comment="
				+ comment + ", commentCount=" + commentCount + ", readingCount=" + readingCount + ", modifyTime="
				+ modifyTime + ", completedSequence=" + completedSequence + ", attended=" + attended + ", joinStatus="
				+ joinStatus + ", official=" + official + ", attendees=" + attendees + ", pinfanPics=" + pinfanPics
				+ ", rates=" + rates + ", status=" + status + "]";
	}

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

	public Boolean getOfficial() {
		return official;
	}

	public void setOfficial(Boolean official) {
		this.official = official;
	}

	public Boolean getStick() {
		return stick;
	}

	public void setStick(Boolean stick) {
		this.stick = stick;
	}

	public String getStickOrder() {
		return stickOrder;
	}

	public void setStickOrder(String stickOrder) {
		this.stickOrder = stickOrder;
	}
	
}
