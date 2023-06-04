package com.poly.asm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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

//	@PostMapping("/ui-user")
//	public String PostUIUsetAdmin(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model) {
//
//		model.addAttribute("ui_user", "active");
//		return "/admin/views/ui-user";
//	}

	@RequestMapping("/ui-brand")
	public String listUIBrandAdmin(Model model) {
		model.addAttribute("ui_brand", "active");
		Brand item = new Brand();
		model.addAttribute("brand", item);
		return "/admin/views/ui-brand";
	}

	@RequestMapping("/ui-category")
	public String listUICategoryAdmin(Model model) {
		model.addAttribute("ui_category", "active");
		Category item = new Category();
		model.addAttribute("category", item);

		return "/admin/views/ui-category";
	}

	@RequestMapping("/ui-product")
	public String listUIProductAdmin(@ModelAttribute("product") Product product, Model model) {
		model.addAttribute("ui_product", "active");
		DetailedImage detailedImage = new DetailedImage();
		model.addAttribute("detailedImage", detailedImage);
//		Danh mục
		Category itemCategory = new Category();
		model.addAttribute("category", itemCategory);
		List<Category> itemsCategories = daoCategory.findAll();
		model.addAttribute("categories", itemsCategories);
// Nhãn hàng
		Brand itemBrand = new Brand();
		model.addAttribute("brand", itemBrand);
		List<Brand> itemsBrand = daoBrandRepository.findAll();
		model.addAttribute("brands", itemsBrand);

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
		Product item = new Product();
		model.addAttribute("product", item);
		List<Product> items = daoProduct.findAll();
		model.addAttribute("products", items);

//		detailed Image
		DetailedImage detailedImage = new DetailedImage();
		model.addAttribute("detailedImage", detailedImage);
		List<DetailedImage> listDetailedImages = daoDetailedImage.findAll();
		model.addAttribute("DetailedImages", listDetailedImages);
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
