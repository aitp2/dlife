package com.aitp.web.aitpfront.service.beans;

public class AuthInfo {
    private String appid;
    private String appsecret;
    private String accessTokenUrl;
    private String refreshTokenUrl;
    private String snsapiUserinfoUrl;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getRefreshTokenUrl() {
        return refreshTokenUrl;
    }

    public void setRefreshTokenUrl(String refreshTokenUrl) {
        this.refreshTokenUrl = refreshTokenUrl;
    }

    public String getSnsapiUserinfoUrl() {
        return snsapiUserinfoUrl;
    }

    public void setSnsapiUserinfoUrl(String snsapiUserinfoUrl) {
        this.snsapiUserinfoUrl = snsapiUserinfoUrl;
    }
}
