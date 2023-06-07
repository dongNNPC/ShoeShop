package com.poly.asm.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterConfigAuth implements WebMvcConfigurer {

	@Autowired
	InterceptorAuth auth;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(auth).addPathPatterns("/shoeshop/admin/**").excludePathPatterns("/shoeshop/index");
	}

}