package com.aitp.web.common.service.dto;

public class WechatMessageData {

	private String keyName;
	
	private String value;
	
	private String color;

	
	
	
	public WechatMessageData(String keyName, String value, String color) {
		super();
		this.keyName = keyName;
		this.value = value;
		this.color = color;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
	
}
