package com.aitp.web.common.service.beans;

import java.util.HashMap;
import java.util.Map;

public class Message {

	private String touser;
	
	private String template_id;
	
	private Map<String,TempData> data;

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


	
	
	public Map<String, TempData> getData() {
		return data;
	}

	public void setData(Map<String, TempData> data) {
		this.data = data;
	}

	public void addTempData(String key,TempData d){
		if(data==null){
			data = new HashMap<String,TempData>();
		}
		data.put(key, d);
	}
	
}
