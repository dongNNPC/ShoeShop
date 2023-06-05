package com.poly.asm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.asm.dao.ProductRepository;
import com.poly.asm.model.Product;



@Controller
@RequestMapping("/shoeshop")
public class IndexController {
	
	
	@Autowired ProductRepository daoPro;
	
	@RequestMapping("/index")
	public String index ( Model model ,@RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 3);
		Product item = new Product();
		model.addAttribute("item", item);
		List<Product> items = daoPro.findAll();
		model.addAttribute("items", items);
		//trả về view
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
