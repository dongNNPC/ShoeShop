package com.poly.asm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.asm.dao.BrandRepository;
import com.poly.asm.dao.CategoryRepository;
import com.poly.asm.dao.DetailedImageRepository;
import com.poly.asm.dao.DetailedInvoiceRepository;
import com.poly.asm.dao.ProductRepository;
import com.poly.asm.dao.StockReceiptRepository;
import com.poly.asm.dao.UserRepository;
import com.poly.asm.model.Brand;
import com.poly.asm.model.Category;
import com.poly.asm.model.DetailedImage;
import com.poly.asm.model.Product;
import com.poly.asm.model.Report;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;

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
	
	@Autowired 
	StockReceiptRepository daoReceiptRepository;
	
	@Autowired
	DetailedInvoiceRepository daodetailedInvoiceRepository;
	
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

		
		   //tổng số lượng sản phẩm
		   List<Report> reports =  daoProduct.getTotalQuantity();
		   model.addAttribute( "rpIndex" ,reports);
		
		   //tổng số lượng nhập kho 
		   List<Report> totalInventory =  daoReceiptRepository.getTotalInventory();
		   model.addAttribute( "totalInventory" , totalInventory);
		   
		   //tổng doanh thu
		   List<Report> TotalRevenue =  daoProduct.getTotalRevenue();
		   model.addAttribute( "TotalRevenue" , TotalRevenue);
		   
		   //tổng oder
		   List<Report> TotalODer =  daodetailedInvoiceRepository.getTotalODer();
		   model.addAttribute( "TotalODer" , TotalODer);

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
	public String listBardAdmin(Model model, @ModelAttribute("user") User user,
			@RequestParam("keywords") Optional<String> kw, @RequestParam("p") Optional<Integer> p,
			@RequestParam("field") Optional<String> field, HttpServletRequest request) {
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

		String order = request.getParameter("sortOrder");

		Sort sort = Sort.by(Direction.ASC, field.orElse("name"));
		if (order != null) {
			if (order.equals("desc")) {
				sort = Sort.by(Direction.DESC, field.orElse("name"));
			}
		}

		session.remove("keywords");
		String kwords = kw.orElse(session.get("keywords"));
		session.set("keywords", kwords, 30);
		model.addAttribute("keywords", session.get("keywords"));
		Pageable pageable = PageRequest.of(p.orElse(0), 5, sort);

		if (kw.isPresent()) {
			Page<Brand> page = daoBrandRepository.findAllByNameLike("%" + kwords + "%", pageable);
			model.addAttribute("brands", page);
		} else {
			Page<Brand> items = daoBrandRepository.findAll(pageable);
			model.addAttribute("brands", items);
		}

//		Brand item = new Brand();
//		model.addAttribute("brand", item);
//
//		List<Brand> items = daoBrandRepository.findAll();
//		model.addAttribute("brands", items);

		return "/admin/views/list-brand";
	}

	@RequestMapping("/list-category")
	public String listCategoryAdmin(Model model, @ModelAttribute("user") User user,
			@RequestParam("keywords") Optional<String> kw, @RequestParam("p") Optional<Integer> p,
			@RequestParam("field") Optional<String> field, HttpServletRequest request) {

		model.addAttribute("list_category", "active");

		if (session.get("user") == null) {
			// Xử lý khi session là null
			// Ví dụ: Tạo một đối tượng User mặc định
			User defaultUser = new User();
			model.addAttribute("user", defaultUser);
		} else {
			user = session.get("user");
			model.addAttribute("user", user);
		}

		String order = request.getParameter("sortOrder");

		Sort sort = Sort.by(Direction.ASC, field.orElse("name"));
		if (order != null) {
			if (order.equals("desc")) {
				sort = Sort.by(Direction.DESC, field.orElse("name"));
			}
		}

		session.remove("keywords");
		String kwords = kw.orElse(session.get("keywords"));
		session.set("keywords", kwords, 30);
		model.addAttribute("keywords", session.get("keywords"));
		Pageable pageable = PageRequest.of(p.orElse(0), 5, sort);

		if (kw.isPresent()) {
			Page<Category> page = daoCategory.findAllByNameLike("%" + kwords + "%", pageable);
			model.addAttribute("categories", page);
		} else {
			Page<Category> items = daoCategory.findAll(pageable);
			model.addAttribute("categories", items);
		}

//		Category item = new Category();
//		model.addAttribute("category", item);
//		List<Category> items = daoCategory.findAll();
//
//		model.addAttribute("categories", items);
		return "/admin/views/list-category";
	}

	@RequestMapping("/list-product")
	public String listProductAdmin(Model model, @ModelAttribute("user") User user,
			@RequestParam("keywords") Optional<String> kw, @RequestParam("p") Optional<Integer> p,
			@RequestParam("field") Optional<String> field, HttpServletRequest request) {
		model.addAttribute("list_product", "active");

		if (session.get("user") == null) {
			// Xử lý khi session là null
			// Ví dụ: Tạo một đối tượng User mặc định
			User defaultUser = new User();
			model.addAttribute("user", defaultUser);
		} else {
			user = session.get("user");
			model.addAttribute("user", user);
		}
		String order = request.getParameter("sortOrder");

		Sort sort = Sort.by(Direction.ASC, field.orElse("price"));
		if (order != null) {
			if (order.equals("desc")) {
				sort = Sort.by(Direction.DESC, field.orElse("price"));
			}
		}

		session.remove("keywords");
		String kwords = kw.orElse(session.get("keywords"));
		session.set("keywords", kwords, 30);
		model.addAttribute("keywords", session.get("keywords"));
		Pageable pageable = PageRequest.of(p.orElse(0), 5, sort);
		if (kw.isPresent()) {
			Page<Product> page = daoProduct.findAllByNameLike("%" + kwords + "%", pageable);
			model.addAttribute("products", page);
		} else {
			Page<Product> items = daoProduct.findAll(pageable);
			model.addAttribute("products", items);
		}
		return "/admin/views/list-product";
	}

	@RequestMapping("/list-user")
	public String listUserAdmin(Model model, @ModelAttribute("user") User user,
			@RequestParam("keywords") Optional<String> kw, @RequestParam("p") Optional<Integer> p,
			@RequestParam("field") Optional<String> field, HttpServletRequest request) {
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

		String order = request.getParameter("sortOrder");

		Sort sort = Sort.by(Direction.ASC, field.orElse("name"));
		if (order != null) {
			if (order.equals("desc")) {
				sort = Sort.by(Direction.DESC, field.orElse("name"));
			}
		}

		session.remove("keywords");
		String kwords = kw.orElse(session.get("keywords"));
		session.set("keywords", kwords, 30);
		model.addAttribute("keywords", session.get("keywords"));
		Pageable pageable = PageRequest.of(p.orElse(0), 5, sort);
		if (kw.isPresent()) {
			Page<User> page = daoUser.findAllByNameLike("%" + kwords + "%", pageable);
			model.addAttribute("users", page);
		} else {
			Page<User> items = daoUser.findAll(pageable);
			model.addAttribute("users", items);
		}

//		
//		User item = new User();
//		model.addAttribute("user", item);
//		List<User> items = daoUser.findAll();
//		model.addAttribute("users", items);

		return "/admin/views/list-user";
	}

}
