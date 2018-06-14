package com.aitp.dlife.fitness.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.aitp.dlife.fitness")
public class FeignConfiguration {

}
