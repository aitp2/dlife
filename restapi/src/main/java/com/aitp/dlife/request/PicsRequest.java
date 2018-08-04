package com.aitp.dlife.request;


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.Size;

/**
 * A DTO for the Pics entity.
 */
public class PicsRequest implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 4371714732305482593L;
	@Size(max = 255)
    private String ossPath;

	public String getOssPath() {
		return ossPath;
	}

	public void setOssPath(String ossPath) {
		this.ossPath = ossPath;
	}


   
}
