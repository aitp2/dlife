package com.aitp.web.common.service.enums;

public enum WeChatErrCode {

	 zero(0),minus_one(-1),other(-11);
	 
	 private Integer code;

	private WeChatErrCode(Integer code) {
		this.code = code;
	}
	


	public Integer getCode() {
		return code;
	}




	public void setCode(Integer code) {
		this.code = code;
	}



	public static WeChatErrCode valueof(Integer code){
		for(WeChatErrCode errCode : values()){
			if(errCode.getCode().equals(code)){
			return errCode;
			}
		}
		return other;
	}
	
	
}
