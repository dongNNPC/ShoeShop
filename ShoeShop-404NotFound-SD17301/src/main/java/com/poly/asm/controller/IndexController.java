package com.poly.asm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/shoeshop")
public class IndexController {
	@RequestMapping("/index")
	public String index () {
		
		return "/index";
	}
	@RequestMapping("/details")
	public String details () {
		
		return "/views/details";
	}
	
	@RequestMapping("/giohang")
	public String giohang () {
		
		return "/views/giohang";
	}
	
	@RequestMapping("/thanhtoan")
	public String thanhtoan() {
		
		return "/views/thanhtoan";
	}
	@RequestMapping("/profile")
	public String profile() {
		
		return "/views/profile";
	}
	
//	   @RequestMapping("/login")
//	    public String login() {
//	        return "/modal/modalLogin";
//	    }
	 
}
