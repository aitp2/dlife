package com.aitp.web.common.service.wechat;

import com.aitp.web.common.service.beans.AuthInfo;
import com.aitp.web.common.service.beans.Ticket;
import com.aitp.web.common.service.beans.Token;

public interface AuthTool {
    /**
     * 网页授权access_token
     * @param authInfo
     * @return
     */
    public Token loadAccessToken(AuthInfo authInfo);

    /**
     * 普通access_token
     * @param authInfo
     * @return
     */
    public Token loadToken(AuthInfo authInfo);

    /**
     * 获得jsapi_ticket
     * @param authInfo
     * @return
     */
    public Ticket loadTicket(AuthInfo authInfo);
}
