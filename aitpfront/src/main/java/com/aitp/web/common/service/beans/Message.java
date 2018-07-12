package com.aitp.web.common.service.beans;

import java.util.List;

public class Message {

	private String touser;
	
	private String template_id;
	
	private String url;
	
	private List<TempData> datas;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<TempData> getDatas() {
		return datas;
	}

	public void setDatas(List<TempData> datas) {
		this.datas = datas;
	}
	
	
}
