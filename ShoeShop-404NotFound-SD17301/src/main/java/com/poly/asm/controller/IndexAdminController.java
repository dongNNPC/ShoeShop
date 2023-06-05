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
import com.poly.asm.service.SessionService;

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

	@Autowired
	SessionService session;

	@RequestMapping("/index")
	public String indexAdmin(Model model, @ModelAttribute("user") User user) {
		model.addAttribute("index", "active");

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
//		System.out.println(user.getImage() + "admin");

		return "/admin/index";
	}

	@RequestMapping("/ui-user")
	public String listUIUsetAdmin(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("ui_user", "active");

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
		return "/admin/views/ui-user";
	}

//	@PostMapping("/ui-user")
//	public String PostUIUsetAdmin(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model) {
//
//		model.addAttribute("ui_user", "active");
//		return "/admin/views/ui-user";
//	}

	@RequestMapping("/ui-brand")
	public String listUIBrandAdmin(Model model, @ModelAttribute("user") User user) {
		model.addAttribute("ui_brand", "active");

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
		Brand item = new Brand();
		model.addAttribute("brand", item);
		return "/admin/views/ui-brand";
	}

	@RequestMapping("/ui-category")
	public String listUICategoryAdmin(Model model, @ModelAttribute("user") User user) {
		model.addAttribute("ui_category", "active");
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

		Category item = new Category();
		model.addAttribute("category", item);

		return "/admin/views/ui-category";
	}

	@RequestMapping("/ui-product")
	public String listUIProductAdmin(@ModelAttribute("product") Product product, Model model,
			@ModelAttribute("user") User user) {
		model.addAttribute("ui_product", "active");

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
	public String listBardAdmin(Model model, @ModelAttribute("user") User user) {
		model.addAttribute("list_brand", "active");

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

		Brand item = new Brand();
		model.addAttribute("brand", item);

		List<Brand> items = daoBrandRepository.findAll();
		model.addAttribute("brands", items);

		return "/admin/views/list-brand";
	}

	@RequestMapping("/list-category")
	public String listCategoryAdmin(Model model, @ModelAttribute("user") User user) {
		model.addAttribute("list_category", "active");

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

		Category item = new Category();
		model.addAttribute("category", item);
		List<Category> items = daoCategory.findAll();

		model.addAttribute("categories", items);
		return "/admin/views/list-category";
	}

	@RequestMapping("/list-product")
	public String listProductAdmin(Model model, @ModelAttribute("user") User user) {
		model.addAttribute("list_product", "active");

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
	public String listUserAdmin(Model model, @ModelAttribute("user") User user) {
		model.addAttribute("list_user", "active");

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
