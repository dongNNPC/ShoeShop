package com.poly.asm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.asm.dao.CategoryRepository;
import com.poly.asm.dao.ProductRepository;
import com.poly.asm.model.Category;
import com.poly.asm.model.NewProductTop10;
import com.poly.asm.model.Product;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;
import com.poly.asm.service.ShoppingCartService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/shoeshop")
public class IndexController {
	@Autowired
	private ShoppingCartService cart; // Tiêm Spring Bean đã viết ở bài trước

	@Autowired
	ProductRepository daoPro;

	@Autowired
	CategoryRepository daoCategoryRepository;

	@Autowired
	SessionService session;

	public User checkUser(Model model) {
		User user = new User();
		if (session.get("user") == null) {
			User defaultUser = new User();
			model.addAttribute("user", defaultUser);
			model.addAttribute("loggedIn", false);
		} else {
			user = session.get("user");
			model.addAttribute("loggedIn", true);
			model.addAttribute("user", user);
			model.addAttribute("image", user.getImage());

		}
		return user;
	}

	@RequestMapping("/index")
	public String index(Model model, @RequestParam("p") Optional<Integer> p, @ModelAttribute User user) {

		checkUser(model);
//		Giỏ hàng
		List<Product> products = new ArrayList<>(cart.getItems());
		List<Product> products2 = daoPro.findAll();
		List<Product> products3 = new ArrayList<>();
		double totalAmount = 0.0;
		for (Product p1 : products) {
			for (Product p2 : products2) {
				if (p1.getId().equalsIgnoreCase(p2.getId())) {
					products3.add(p2);
					totalAmount += p2.getPrice();
				}
			}
		}
		model.addAttribute("cart", products3);
		// tạo biến Tổng ti lưu tạm trong modal
		model.addAttribute("totalAmount", totalAmount);

		// đổ tất cả sản phẩm
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

		// đổ 10 sản phẩm mới v
		Pageable pageableTop10 = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "orderDate"));
		Page<NewProductTop10> pageResult = daoPro.getNewProductTop10(pageableTop10);
		List<NewProductTop10> newProductTop10 = pageResult.getContent();
		model.addAttribute("newProductTop10", newProductTop10);
		// trả về view
		return "/index";
	}

	// xoá trong modalCart
	@RequestMapping("/remove")
	public String removeFromCart(@RequestParam("productId") String productId, HttpSession session,
			RedirectAttributes redirectAttributes) {
		cart.remove(productId);

		int cartItemCount = session.getAttribute("cartItemCount") != null ? (int) session.getAttribute("cartItemCount")
				: 0;
		if (cartItemCount > 0) {
			session.setAttribute("cartItemCount", cartItemCount - 1);
		}

		redirectAttributes.addAttribute("modalCartOpen", "true"); // Truyền tham số để hiển thị lại modalCart
		return "redirect:/shoeshop/index";
	}

//	@RequestMapping("/thanhtoan")
//	public String thanhtoan() {
//
//		return "/views/thanhtoan";
//	}

	@RequestMapping("/profile")
	public String profile() {

		return "/views/profile";
	}

//	   @RequestMapping("/login")
//	    public String login() {
//	        return "/modal/modalLogin";
//	    }

}
