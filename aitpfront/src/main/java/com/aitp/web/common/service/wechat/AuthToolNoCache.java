package com.aitp.web.common.service.wechat;

import com.aitp.web.common.service.beans.AuthInfo;
import com.aitp.web.common.service.beans.Ticket;
import com.aitp.web.common.service.beans.Token;
import com.aitp.web.common.service.utils.HttpUtil;
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

    /**
     * 普通access_token
     *
     * @param authInfo
     * @return
     */
    @Override
    public Token loadToken(AuthInfo authInfo) {
        Token token=new Token();
        String tokenInfo = HttpUtil.doGetJson(authInfo.getAccessTokenUrl());
        if(StringUtils.isNotBlank(tokenInfo)){
            JSONObject jsonObject = JSONObject.parseObject(tokenInfo);
            if (jsonObject.containsKey("errcode")){
                token.setErrcode(jsonObject.getString("errcode"));
                token.setErrmsg(jsonObject.getString("errmsg"));
            }else{
                token.setAccess_token(jsonObject.getString("access_token"));
                token.setExpires_in(jsonObject.getString("expires_in"));
            }
        }
        return token;
    }

    /**
     * 获得jsapi_ticket
     *
     * @param authInfo
     * @return
     */
    @Override
    public Ticket loadTicket(AuthInfo authInfo) {
        Ticket ticket=new Ticket();
        String ticketInfo = HttpUtil.doGetJson(authInfo.getJsapiTicketUrl());
        if(StringUtils.isNotBlank(ticketInfo)) {
            JSONObject jsonObject = JSONObject.parseObject(ticketInfo);
            ticket.setErrcode(jsonObject.getString("errcode"));
            ticket.setErrmsg(jsonObject.getString("errmsg"));
            ticket.setTicket(jsonObject.getString("ticket"));
            ticket.setExpires_in(jsonObject.getString("expires_in"));
        }
        return ticket;
    }
}
