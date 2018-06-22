package com.aitp.web.aitpfront.service.wechat;

import com.aitp.web.aitpfront.service.beans.AuthInfo;
import com.aitp.web.aitpfront.service.beans.Token;

public interface AuthTool {
    public Token loadAccessToken(AuthInfo authInfo);
}
