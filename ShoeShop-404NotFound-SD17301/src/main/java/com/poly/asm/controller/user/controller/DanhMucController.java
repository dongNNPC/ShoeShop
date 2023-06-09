package com.poly.asm.controller.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.controller.IndexController;
import com.poly.asm.dao.CategoryRepository;
import com.poly.asm.dao.ProductRepository;
import com.poly.asm.model.Category;
import com.poly.asm.model.Product;
import com.poly.asm.model.User;

@Controller
@RequestMapping("/shoeshop")
public class DanhMucController {

	@Autowired
	CategoryRepository daoCategoryRepository;
	@Autowired
	ProductRepository daoProductRepository;
	@Autowired
	IndexController indexController;
//	
	@GetMapping("/danhmuc-sanpham/{id}")
	public String danhmuc(Model model,@PathVariable("id") String id) {
		
		List<Category> categories = daoCategoryRepository.findAll(); //dùng dao của danh mục
		model.addAttribute("category", categories);
		indexController.checkUser(model);
		List<Product> products = daoProductRepository.findAll();
		List<Product> products2 = new ArrayList<>();	
//			Category category = new Category();
			for (Product product : products) {
				if (product.getCategory().getId().equals(id)) {
					products2.add(product);
					

				}
			}
			model.addAttribute("items", products2);
		
		return "views/danhmuc";
		
	}

	}
	

