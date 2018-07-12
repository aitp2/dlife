package com.aitp.web.common.service.strategy;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.aitp.web.common.service.beans.Response;
import com.aitp.web.common.service.enums.WeChatErrCode;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class WeChatApisHttpClientReTryStrategy implements ServiceUnavailableRetryStrategy{

	
	  private int executionCount;

	  private long retryInterval;
	    
	  
	  WeChatApisHttpClientReTryStrategy(Builder builder) {

	        this.executionCount = builder.executionCount;

	        this.retryInterval = builder.retryInterval;
	    }
	  
	@Override
	public boolean retryRequest(HttpResponse response, int executionCount, HttpContext context) {
		  Gson gson = new Gson();
		  Response response2;
		try {
			response2 = gson.fromJson(EntityUtils.toString(response.getEntity(), "UTF-8"),Response.class);
	
			if (response2.getErrcode().equals(WeChatErrCode.minus_one.getCode()) && this.executionCount < executionCount) {
				return true;
			}
		} catch (JsonSyntaxException | ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public long getRetryInterval() {
		 return this.retryInterval;
	}
	
   
	public static final class Builder {

        private int executionCount;

        private long retryInterval;

 
        public Builder() {

            executionCount = 3;

            retryInterval = 1000;

        }
 
        public Builder executionCount(int executionCount) {

            this.executionCount = executionCount;

            return this;

        }

 
        public Builder retryInterval(long retryInterval) {

            this.retryInterval = retryInterval;

            return this;

        }

        public WeChatApisHttpClientReTryStrategy build() {

            return new WeChatApisHttpClientReTryStrategy(this);
        }

    }

}
