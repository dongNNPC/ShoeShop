package com.poly.asm.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterConfigLogin implements WebMvcConfigurer {

	@Autowired
	InterceptorLogin login;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(login).addPathPatterns("/shoeshop/log-out", "/shoeshop/ChangePass",
				"/shoeshop/ChangeRePass-Change", "/shoeshop/Forget").excludePathPatterns("/shoeshop/index");
	}

}