package com.aitp.dlife.pinfan.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.aitp.dlife.pinfan")
public class FeignConfiguration {

}
