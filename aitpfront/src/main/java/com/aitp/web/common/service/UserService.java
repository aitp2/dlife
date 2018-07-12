package com.aitp.web.common.service;

import com.aitp.web.common.service.dto.WechatUserDTO;
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

    /**
     * 根据wechatUserId 查询openid
     * @param apiPath
     * @param wechatUserId
     * @return
     */
    JSONObject getUserByWechatUserId(String apiPath,String wechatUserId);
}
