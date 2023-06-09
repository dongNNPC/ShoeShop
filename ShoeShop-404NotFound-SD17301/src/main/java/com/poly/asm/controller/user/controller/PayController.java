package com.poly.asm.controller.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.controller.IndexController;
import com.poly.asm.dao.ProductRepository;
import com.poly.asm.dao.UserRepository;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;

@Controller
@RequestMapping("/shoeshop")
public class PayController {
	@Autowired
	SessionService session;
	@Autowired
	UserRepository dao; // user
	@Autowired
	ProductRepository Pdao; // sản phẩm

	@Autowired
	private IndexController indexController;

	@RequestMapping("/thanhtoan")
	public String thanhtoan(@ModelAttribute("user") User user, Model model) {
		indexController.checkUser(model);
		return "/views/thanhtoan";
	}

}
