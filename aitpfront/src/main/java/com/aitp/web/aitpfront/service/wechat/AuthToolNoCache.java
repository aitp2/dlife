package com.aitp.web.aitpfront.service.wechat;

import com.aitp.web.aitpfront.service.beans.AuthInfo;
import com.aitp.web.aitpfront.service.beans.Token;
import com.aitp.web.aitpfront.service.utils.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component("authToolNoCache")
public class AuthToolNoCache implements AuthTool{

    @Override
    public Token loadAccessToken(AuthInfo authInfo) {
        Token token=new Token();
        String tokenInfo = HttpUtil.doGetJson(authInfo.getAccessTokenUrl());
        if(StringUtils.isNotBlank(tokenInfo)){
            JSONObject jsonObject = JSONObject.parseObject(tokenInfo);
            token.setAccess_token(jsonObject.getString("access_token"));
            token.setRefresh_token(jsonObject.getString("refresh_token"));
            token.setExpires_in(jsonObject.getString("expires_in"));
            token.setOpenid(jsonObject.getString("openid"));
            token.setScope(jsonObject.getString("scope"));
        }
        return token;
    }
}
