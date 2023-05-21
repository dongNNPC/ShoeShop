package com.poly.asm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/shoeshop")
public class Index {
	@RequestMapping("/index")
	public String index () {
		
		return "/index";
	}
}
