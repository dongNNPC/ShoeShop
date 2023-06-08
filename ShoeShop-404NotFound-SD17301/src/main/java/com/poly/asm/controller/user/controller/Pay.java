package com.poly.asm.controller.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;

@Controller
@RequestMapping("/shoeshop")
public class Pay {
	@Autowired
	SessionService session;

	@RequestMapping("/thanhtoan")
	public String thanhtoan(@ModelAttribute("user") User user, Model model) {
		if (session.get("user") == null) {
			// Xử lý khi session là null
			// Ví dụ: Tạo một đối tượng User mặc định
			User defaultUser = new User();
			model.addAttribute("user", defaultUser);
		} else {
			user = session.get("user");
			// System.out.println(user.getImage() + "ssssssssssssssssssssssssssss");
			model.addAttribute("user", user);
		}
		return "/views/thanhtoan";
	}

}
