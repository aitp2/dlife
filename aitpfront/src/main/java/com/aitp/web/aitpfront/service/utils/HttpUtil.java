package com.aitp.web.aitpfront.service.utils;
import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * http 工具类
 */
public class HttpUtil {
    public static String doGetJson(String urlStr){
        String result="";
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(urlStr);
        try {
            HttpResponse response = client.execute(get);
            int code = response.getStatusLine().getStatusCode();
            if(code==200){
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
      return result;
    }

    public static String doPostJson(String urlStr,JSONObject jsonObject){
        String respContent = null;
        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(urlStr);
            StringEntity entity = new StringEntity(jsonObject.toString(),"utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            HttpResponse resp = client.execute(httpPost);
            if(resp.getStatusLine().getStatusCode() == 201) {
                HttpEntity resultData = resp.getEntity();
                respContent = EntityUtils.toString(resultData,"UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respContent;
    }

    public static void main(String[] args) {
        String urlStr = "http://localhost:9060/api/wechat-users";
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("avatar","http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46");
        jsonObject.put("openId","66677788888");
        jsonObject.put("nickName","Tom");
        jsonObject.put("sex",true);
        String resultData = HttpUtil.doPostJson(urlStr,jsonObject);
        System.out.println(resultData);
    }
}
