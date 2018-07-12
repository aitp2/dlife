package com.aitp.web.common.service.beans;

public class TempData {

	private String Key;
	
	private String Value;

	
	public TempData(String key, String value) {
		super();
		Key = key;
		Value = value;
	}

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}
	
	
	
}
