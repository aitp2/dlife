package com.aitp.web.aitpfront.service.wechat;

import com.aitp.web.aitpfront.service.beans.AuthInfo;
import com.aitp.web.aitpfront.service.beans.Token;
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

    public Token loadAccessToken(AuthInfo authInfo){
        return authTool.loadAccessToken(authInfo);
    }
}
