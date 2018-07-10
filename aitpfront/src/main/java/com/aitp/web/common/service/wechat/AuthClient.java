package com.aitp.web.common.service.wechat;

import com.aitp.web.common.service.beans.AuthInfo;
import com.aitp.web.common.service.beans.Ticket;
import com.aitp.web.common.service.beans.Token;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
@Component
public class AuthClient {
    /**
     * 采用策略设计模式
     * 目前的实现方案是无缓存方案，后期如果使用了全局缓存，可替换为有缓存的实现类即可
     */
    @Resource(name = "authToolNoCache")
    private AuthTool authTool;

    /**
     * 网页授权access_token
     * @param authInfo
     * @return
     */
    public Token loadAccessToken(AuthInfo authInfo){
        return authTool.loadAccessToken(authInfo);
    }

    /**
     * 普通access_token
     * @param authInfo
     * @return
     */
    public Token loadToken(AuthInfo authInfo){
        return authTool.loadToken(authInfo);
    }

    /**
     * 获得jsapi_ticket
     * @param authInfo
     * @return
     */
    public Ticket loadTicket(AuthInfo authInfo){
        return authTool.loadTicket(authInfo);
    }

}
