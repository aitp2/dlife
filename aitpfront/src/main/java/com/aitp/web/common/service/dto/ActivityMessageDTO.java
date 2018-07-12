package com.aitp.web.common.service.dto;

public class ActivityMessageDTO {

	
    private String authToken;
    
    private String touser;

	public String getAuthToken() {
		return authToken;
	}

    
	public String getTouser() {
		return touser;
	}



	public void setTouser(String touser) {
		this.touser = touser;
	}



	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	
	
}
