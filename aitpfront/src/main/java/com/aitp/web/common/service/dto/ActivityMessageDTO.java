package com.aitp.web.common.service.dto;

public class ActivityMessageDTO {

	
    private String authToken;
    
    private String touser;

    private String title;

    private String action;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
