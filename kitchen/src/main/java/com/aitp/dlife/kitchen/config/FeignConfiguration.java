package com.aitp.dlife.kitchen.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.aitp.dlife.kitchen")
public class FeignConfiguration {

}
