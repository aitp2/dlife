package com.aitp.dlife.response;


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.Size;

/**
 * A DTO for the Pics entity.
 */
public class PicsResponse implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4371714732305482593L;
	
	/**
	 * 图片路径
	 */
    private String ossPath;

	public String getOssPath() {
		return ossPath;
	}

	public void setOssPath(String ossPath) {
		this.ossPath = ossPath;
	}

	@Override
	public String toString() {
		return "PicsResponse [ossPath=" + ossPath + "]";
	}
	
   
}
