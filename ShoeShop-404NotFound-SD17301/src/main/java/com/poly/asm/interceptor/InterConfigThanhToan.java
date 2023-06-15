package com.poly.asm.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterConfigThanhToan implements WebMvcConfigurer {

	@Autowired
	InterceptorThanhToan thanhToan;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(thanhToan).addPathPatterns("/shoeshop/thanhtoan")
				.excludePathPatterns("/shoeshop/index");
	}

}