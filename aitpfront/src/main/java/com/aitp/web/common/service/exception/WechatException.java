package com.aitp.web.common.service.exception;


public class WechatException extends RuntimeException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer errorCode;
	
	private String message;

	public WechatException(Integer errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}



	public Integer getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
