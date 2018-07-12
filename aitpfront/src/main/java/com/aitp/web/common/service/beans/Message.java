package com.aitp.web.common.service.beans;

import java.util.ArrayList;
import java.util.List;

public class Message {

	private String touser;
	
	private String template_id;
	
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


	public List<TempData> getDatas() {
		return datas;
	}

	public void setDatas(List<TempData> datas) {
		this.datas = datas;
	}
	
	public void addTempData(TempData data){
		if(datas==null){
			datas = new ArrayList<>();
		}
		datas.add(data);
	}
	
}
