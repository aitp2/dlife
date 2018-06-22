package com.aitp.web.aitpfront.service.utils;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
      return result;
    }

    public static void main(String[] args) {
        String urlStr = "http://47.97.202.185:8080/api/account";
        HttpUtil.doGetJson(urlStr);
    }
}
