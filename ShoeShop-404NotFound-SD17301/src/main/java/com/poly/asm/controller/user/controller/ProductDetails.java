package com.poly.asm.controller.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.dao.CategoryRepository;
import com.poly.asm.dao.DetailedImageRepository;
import com.poly.asm.dao.ProductRepository;
import com.poly.asm.model.Category;
import com.poly.asm.model.DetailedImage;
import com.poly.asm.model.Product;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;

@Controller
@RequestMapping("/shoeshop")
public class ProductDetails {

	@Autowired
	ProductRepository daoProductRepository;

	@Autowired
	DetailedImageRepository daoDetailedImageRepository;

	@Autowired
	CategoryRepository daoCategoryRepository;

	@Autowired
	SessionService session;

	@GetMapping("/details/{id}")
	public String details(@PathVariable("id") String id, Model model, @ModelAttribute("product") Product product,
			@ModelAttribute("user") User user) {
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

		List<Product> products = daoProductRepository.findAll();
		List<DetailedImage> detailedImages = daoDetailedImageRepository.findAll();
		List<Category> categories = daoCategoryRepository.findAll();

		for (Product p : products) {
			if (p.getId().equals(id)) {
				System.out.println(p.getPrice());
				model.addAttribute("p", p);
				for (DetailedImage d : detailedImages) {
					if (p.getId().equalsIgnoreCase(d.getProduct().getId())) {
						model.addAttribute("d", d);

					}
				}
				for (Category c : categories) {
					if (p.getCategory().getId().equalsIgnoreCase(c.getId())) {
						model.addAttribute("c", c);
					}
				}
			}
		}

		return "/views/details";
	}
}
