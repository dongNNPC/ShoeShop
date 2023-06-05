package com.poly.asm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.asm.dao.CategoryRepository;
import com.poly.asm.dao.ProductRepository;
import com.poly.asm.model.Category;
import com.poly.asm.model.Product;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;

@Controller
@RequestMapping("/shoeshop")
public class IndexController {

	@Autowired
	ProductRepository daoPro;

	@Autowired
	CategoryRepository daoCategoryRepository;

	@Autowired
	SessionService session;

	@RequestMapping("/index")
	public String index(Model model, @RequestParam("p") Optional<Integer> p, @ModelAttribute User user) {

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
	    
	    List<Category> categories = daoCategoryRepository.findAll();
	    model.addAttribute("categories", categories);

	    Pageable pageable = PageRequest.of(p.orElse(0), 8);
	    Page<Product> page = daoPro.findAll(pageable);
	    List<Product> items = page.getContent();
	    model.addAttribute("items", items);
	    model.addAttribute("currentPage", page.getNumber());
	    model.addAttribute("totalPages", page.getTotalPages());

	    Product item = new Product();
	    model.addAttribute("item", item);
	    
	    // trả về view
	    return "/index";
	}


	@RequestMapping("/details")
	public String details() {

		return "/views/details";
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
