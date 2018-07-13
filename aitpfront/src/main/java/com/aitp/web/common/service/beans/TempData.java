package com.aitp.web.common.service.beans;

public class TempData {

	private String color;
	
	private String value;

	
	
	
	public TempData(String value, String color) {
		super();
		this.color = color;
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
