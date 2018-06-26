package com.aitp.web.aitpfront.service;

import com.aitp.web.aitpfront.service.dto.WechatUserDTO;
import com.aitp.web.aitpfront.service.utils.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImp implements UserService{

    @Override
    public JSONObject getUserByOpenid(String apiPath, String openid) {
        String userInfo = HttpUtil.doGetJson(apiPath+"/wechat-users/userinfo/"+openid);
        if(StringUtils.isNotBlank(userInfo)){
            return JSONObject.parseObject(userInfo);
        }
        return null;
    }

    @Override
    public JSONObject createUser(String apiPath, WechatUserDTO wechatUserDTO) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("avatar",wechatUserDTO.getHeadimgurl());
        jsonObject.put("openId",wechatUserDTO.getOpenId());
        jsonObject.put("nickName",wechatUserDTO.getUserName());
//      jsonObject.put("sex",1);
        String resultData=HttpUtil.doPostJson(apiPath+"/api/wechat-users",jsonObject);
        if(StringUtils.isNotBlank(resultData)){
            return JSONObject.parseObject(resultData);
        }
        return null;
    }
}
