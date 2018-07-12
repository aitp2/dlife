package com.aitp.web.common.service.strategy;

import org.apache.http.HttpResponse;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.protocol.HttpContext;

import com.aitp.web.common.service.beans.Response;
import com.aitp.web.common.service.enums.WeChatErrCode;
import com.google.gson.Gson;

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
		  Response response2 = gson.fromJson(response.getEntity().toString(),Response.class);
			if (response2.getErrcode().equals(WeChatErrCode.minus_one.getCode()) && this.executionCount < executionCount) {
				return true;
			}
			else{
				return false;
			}
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
