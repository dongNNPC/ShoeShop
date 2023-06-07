package com.poly.asm.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class InterceptorAuth implements HandlerInterceptor {
	@Autowired
	SessionService session;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
//		session
		User user = session.get("user");
		String error = "";
		String messageString = "";
//		check đăng nhập chưa
		if (user == null) {
			error = "No sign in!";
		}
		// check admin
		else if (user.isAdmin() == false) {
			session.remove("user");
			error = "Not admin!";

		}

		if (error.length() > 0) { // có lỗi
			session.set("security-uri", uri, 1);
			session.remove("mess");
			messageString = "Vui lòng đăng nhập bằng tài khoản của admin!!!";
			session.set("mess", messageString, 1);
			response.sendRedirect("/shoeshop/login?error=" + error);
			return false;
		}
		return true;
	}

}
