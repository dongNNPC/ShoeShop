package com.poly.asm.controller;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.poly.asm.model.User;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/shoeshop")
public class AccountController {

	// trang đăng nhập
	@RequestMapping("/login")
	public String login(@ModelAttribute("user") User user) {
		return "/account/login";
	}

	@PostMapping("/login")
	public String loginCheck(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model) {
		System.out.println(user.getEmail());
		if (user.getEmail().equalsIgnoreCase("user@gmail.com") && user.getPassword().equalsIgnoreCase("123")) {

			return "redirect:/shoeshop/index";
		}
		if (user.getEmail().equalsIgnoreCase("admin@gmail.com") && user.getPassword().equalsIgnoreCase("123")) {

			return "redirect:/shoeshop/admin/index";
		}

		return "/account/login";
	}

	// trang signUp
	@RequestMapping("/signUp")
	public String signUp(@ModelAttribute("user") User user) {
		return "/account/signUp";
	}

	// trang signUp
	@PostMapping("/signUp")
	public String signUp(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model) {
		return "/account/signUp";
	}

	// trang kiểm tra password
	@RequestMapping("/ChangePass")
	public String ChangePass(@ModelAttribute("user") User user) {
		return "/account/ChangePass";
	}

	// trang nhập mật khẩu mới
	@PostMapping("/ChangePass")
	public String ChangePassCheck(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model) {
		if (user.getPassword().equalsIgnoreCase("123")) {

			return "redirect:/shoeshop/ChangeRePass";
		}
		return "/account/ChangePass";
	}

	// trang nhập mật khẩu mới
	@RequestMapping("/ChangeRePass")
	public String ChangeRePass() {
		return "/account/ChangeRePass";
	}

	// trang nhập mật khẩu mới
	@RequestMapping("/Forget")
	public String Forget(@ModelAttribute("user") User user) {
		return "/account/Forget";
	}

	// trang nhập mật khẩu mới
	@PostMapping("/Forget")
	public String ForgetCheck(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model) {
		if (user.getEmail().equalsIgnoreCase("user@gmail.com")) {

			return "redirect:/shoeshop/ChangeRePass";
		}
		return "/account/Forget";
	}
}
