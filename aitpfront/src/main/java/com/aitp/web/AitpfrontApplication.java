package com.aitp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
public class AitpfrontApplication {

	@Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
        factory.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/"));
        return factory;
    }
	
	
    public static void main(String[] args) {
        SpringApplication.run(AitpfrontApplication.class, args);
    }
}
