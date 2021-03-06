package com.aitp.web.common.service.dto;

import java.util.ArrayList;
import java.util.List;

public class ActivityMessageDTO {

	private String touser;

	private String templateID;

	private List<WechatMessageData> wechatMessageDatas;

	public String getTouser() {
		return touser;
	}

	public String getTemplateID() {
		return templateID;
	}

	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public List<WechatMessageData> getWechatMessageDatas() {
		return wechatMessageDatas;
	}

	public void setWechatMessageDatas(List<WechatMessageData> wechatMessageDatas) {
		this.wechatMessageDatas = wechatMessageDatas;
	}

	public List<WechatMessageData> addMessageData(WechatMessageData wData) {
		if (wechatMessageDatas == null) {
			wechatMessageDatas = new ArrayList<>();
		}
		wechatMessageDatas.add(wData);
		return wechatMessageDatas;
	}

}
