package com.aitp.web.common.service.wechat;

import com.aitp.web.common.service.beans.AuthInfo;
import com.aitp.web.common.service.beans.Token;

public interface AuthTool {
    public Token loadAccessToken(AuthInfo authInfo);
}
