package com.poly.asm.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class InterceptorThanhToan implements HandlerInterceptor {
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
			messageString = "Vui lòng đăng nhập!!!";
		} else {
			if (user.isAdmin()) {
				error = "User login!";
				messageString = "User mới được sử dụng quyền này!!!";
			}
		}

		if (error.length() > 0) { // có lỗi
			session.set("security-uri", uri, 1);
			session.remove("mess");

			session.set("mess", messageString, 1);
			response.sendRedirect("/shoeshop/index?error=" + error);
			return false;
		}
		return true;
	}

}
