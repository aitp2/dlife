package com.aitp.web.common.service;

import com.aitp.web.common.service.dto.WechatUserDTO;
import com.aitp.web.common.service.utils.HttpUtil;
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
        jsonObject.put("nickName",HttpUtil.baseEncoder(wechatUserDTO.getUserName()));
        if (wechatUserDTO.getSex().equals("1")){
            jsonObject.put("sex",true);
        }else if(wechatUserDTO.getSex().equals("2")){
            jsonObject.put("sex",false);
        }else{
            jsonObject.put("sex","");
        }
        String resultData=HttpUtil.doPostJson(apiPath+"/wechat-users",jsonObject);
        if(StringUtils.isNotBlank(resultData)){
            return JSONObject.parseObject(resultData);
        }
        return null;
    }
}
