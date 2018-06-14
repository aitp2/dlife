package com.aitp.dlife.comments.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.aitp.dlife.comments")
public class FeignConfiguration {

}
