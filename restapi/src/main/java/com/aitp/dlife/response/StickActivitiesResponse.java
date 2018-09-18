package com.aitp.dlife.response;

import java.io.Serializable;

/**
 * 置顶活动
 * @author k.c.liu
 *
 */
public class StickActivitiesResponse implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7703333040333292649L;

	/**
	 * 活动类型
	 */
	private String channel;
	
	/**
	 * 活动id
	 */
	private String id;
	
	/**
	 * 活动图片url
	 */
	private String url;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
