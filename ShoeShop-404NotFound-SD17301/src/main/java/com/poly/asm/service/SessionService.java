package com.poly.asm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class SessionService {
	@Autowired
	HttpSession session;

	public <T> T get(String name) {
		// Đọc giá trị của attribute từ session và ép kiểu về kiểu dữ liệu T
		return (T) session.getAttribute(name);
	}

	public String getString(String name) {
		return (String) session.getAttribute(name);
	}

	public void set(String name, Object value, int time) {
		// Thiết lập giá trị mới cho attribute trong session
		session.setAttribute(name, value);
		int sessionTimeoutInSeconds = time * 60;
		session.setMaxInactiveInterval(sessionTimeoutInSeconds);
	}

	public void remove(String name) {
		// Xóa attribute khỏi session
		session.removeAttribute(name);
	}
}
