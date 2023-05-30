package com.poly.asm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.dao.BrandRepository;
import com.poly.asm.dao.CategoryRepository;
import com.poly.asm.dao.DetailedImageRepository;
import com.poly.asm.dao.ProductRepository;
import com.poly.asm.dao.UserRepository;
import com.poly.asm.model.Brand;
import com.poly.asm.model.Category;
import com.poly.asm.model.DetailedImage;
import com.poly.asm.model.Product;
import com.poly.asm.model.User;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/shoeshop/admin")
public class IndexAdminController {

	@Autowired
	UserRepository daoUser; // làm việc với bảng User

	@Autowired
	CategoryRepository daoCategory; // Làm việc với Category

	@Autowired
	ProductRepository daoProduct; // Làm việc với product

	@Autowired
	DetailedImageRepository daoDetailedImage; // DetailedImageRepository

	@Autowired
	BrandRepository daoBrandRepository;

	@RequestMapping("/index")
	public String indexAdmin(Model model) {
		model.addAttribute("index", "active");
		return "/admin/index";
	}

	@RequestMapping("/ui-user")
	public String listUIUsetAdmin(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("ui_user", "active");
		return "/admin/views/ui-user";
	}

	@PostMapping("/ui-user")
	public String PostUIUsetAdmin(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model) {
		model.addAttribute("ui_user", "active");
		return "/admin/views/ui-user";
	}

	@RequestMapping("/ui-brand")
	public String listUIBrandAdmin(Model model) {
		model.addAttribute("ui_brand", "active");
		return "/admin/views/ui-brand";
	}

	@RequestMapping("/ui-category")
	public String listUICategoryAdmin(Model model) {
		model.addAttribute("ui_category", "active");
		return "/admin/views/ui-category";
	}

	@RequestMapping("/ui-product")
	public String listUIProductAdmin(Model model) {
		model.addAttribute("ui_product", "active");
		return "/admin/views/ui-product";
	}

	@RequestMapping("/list-brand")
	public String listBardAdmin(Model model) {
		model.addAttribute("list_brand", "active");
		Brand item = new Brand();
		model.addAttribute("brand", item);

		List<Brand> items = daoBrandRepository.findAll();
		model.addAttribute("brands", items);

		return "/admin/views/list-brand";
	}

	@RequestMapping("/list-category")
	public String listCategoryAdmin(Model model) {
		model.addAttribute("list_category", "active");
		Category item = new Category();
		model.addAttribute("category", item);
		List<Category> items = daoCategory.findAll();

		model.addAttribute("categories", items);
		return "/admin/views/list-category";
	}

	@RequestMapping("/list-product")
	public String listProductAdmin(Model model) {
		model.addAttribute("list_product", "active");
		model.addAttribute("active", "active");
		Product item = new Product();
		model.addAttribute("product", item);
		List<Product> items = daoProduct.findAll();
		List<DetailedImage> detailedImages = daoDetailedImage.findAll();
		model.addAttribute("products", items);
		return "/admin/views/list-product";
	}

	@RequestMapping("/list-user")
	public String listUserAdmin(Model model) {
		model.addAttribute("list_user", "active");
		User item = new User();
		model.addAttribute("user", item);
		List<User> items = daoUser.findAll();
		for (User s : items) {
			System.out.print(s.getImage());
		}
		model.addAttribute("users", items);

		return "/admin/views/list-user";
	}

}
