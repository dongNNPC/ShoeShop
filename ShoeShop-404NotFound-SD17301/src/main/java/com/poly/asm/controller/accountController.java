package com.poly.asm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shoeshop")
public class accountController {
	
	//trang đăng nhập
	@RequestMapping("/login")
	public String login () {
		return "/account/login";
	}
	//trang login
	@RequestMapping("/signUp")
	public String signUp () {
		return "/account/signUp";
	}
	//trang kiểm tra password
	@RequestMapping("/ChangePass")
	public String ChangePass () {
		return "/account/ChangePass";
	}
	//trang nhập mật khẩu mới	
	@RequestMapping("/ChangeRePass")
	public String ChangeRePass () {
		return "/account/ChangeRePass";
	}
	//trang nhập mật khẩu mới	
		@RequestMapping("/Forget")
		public String Forget () {
			return "/account/Forget";
		}
}
