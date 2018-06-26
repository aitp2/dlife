package com.aitp.web.aitpfront.service;

import com.aitp.web.aitpfront.service.dto.WechatUserDTO;
import com.alibaba.fastjson.JSONObject;

/**
 *
 */
public interface UserService {
    /**
     * 根据openid查询用户信息
     * @param apiPath
     * @param openid
     * @return
     */
    JSONObject getUserByOpenid(String apiPath, String openid);

    /**
     * 创建用户信息
     * @param apiPath
     * @param wechatUserDTO
     * @return
     */
    JSONObject createUser(String apiPath,WechatUserDTO wechatUserDTO);
}
