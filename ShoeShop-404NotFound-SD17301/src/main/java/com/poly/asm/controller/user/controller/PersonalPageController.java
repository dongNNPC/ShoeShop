package com.poly.asm.controller.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.controller.IndexController;
import com.poly.asm.dao.CategoryRepository;
import com.poly.asm.dao.ProductRepository;
import com.poly.asm.dao.UserRepository;
import com.poly.asm.model.Category;
import com.poly.asm.model.Product;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;
import com.poly.asm.service.ShoppingCartService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/shoeshop")
public class PersonalPageController {

	@Autowired
	UserRepository dao; // dao của user
	@Autowired
	SessionService session;

	@Autowired
	CategoryRepository daoCategoryRepository;

	@Autowired
	ProductRepository daoPro;

	@Autowired
	private ShoppingCartService cart; // Tiêm Spring Bean đã viết ở bài trước

	@Autowired
	private IndexController indexController;

//	
	@GetMapping("/personal-page")
	public String Personal(@ModelAttribute("user") User user, Model model) {
		indexController.checkUser(model);
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
		return "/account/personalpage";

	}

	@PostMapping("/personal-page")
	public String PersonalCheck(@Valid @ModelAttribute("user") User user, Model model, BindingResult rs) {
		if (rs.hasErrors()) {
//			String successMessage = "create failed";
//			model.addAttribute("failed", successMessage);
			System.out.println(rs.toString());
			return "/account/personalpage";
		}
//		a = dao.findById(id).get();
		dao.save(user);
		return "/account/personalpage";

	}

//	//cập nhật
//	@RequestMapping(value = "/personal-page", method = RequestMethod.POST)
//	public String updatePersonalPage(@ModelAttribute("user") User user) {
//	  User currentUser = session.get("user");
//	  
//	  currentUser.setName(user.getName());
//	  currentUser.setEmail(user.getEmail());
//	  currentUser.setPassword(user.getPassword());
//	  currentUser.setPhone(user.getPhone());
//	  currentUser.setAddress(user.getAddress());
//	  
//	  dao.save(currentUser);
//	  
//	  return "redirect:/personal-page/" + currentUser.getID();
//	}
//	@RequestMapping("/update")
//	public String update (Model model, @Valid @ModelAttribute("user") User user, BindingResult result) {
//		if (result.hasErrors()) {
//			String successMessage = "Cập nhật thất bại";
//			model.addAttribute("Updatefailed", successMessage);
//			return "/account/personalpage";
//		}
//		List<User> users = dao.findAll();
//		for (User user2 : users) {
//			if (user2.getID().equalsIgnoreCase(user.getID())) {
//				dao.save(user);
//				return "/account/personalpage";
//			}
//		}
//		String successMessage = "ID Không tồn tại !";
//		model.addAttribute("failed", successMessage);
//		return "/account/personalpage";
//		
//	}
}
