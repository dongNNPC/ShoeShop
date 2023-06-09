package com.poly.asm.controller.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.controller.IndexController;
import com.poly.asm.dao.CategoryRepository;
import com.poly.asm.dao.DetailedImageRepository;
import com.poly.asm.dao.ProductRepository;
import com.poly.asm.model.Category;
import com.poly.asm.model.DetailedImage;
import com.poly.asm.model.Product;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;
import com.poly.asm.service.ShoppingCartService;

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

	@Autowired
	private ShoppingCartService cart;

	@Autowired
	private IndexController indexController;

	@GetMapping("/details/{id}")
	public String details(@PathVariable("id") String id, Model model, @ModelAttribute("product") Product product,
			@ModelAttribute("user") User user) {
		indexController.checkUser(model);

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

		List<Product> products1 = new ArrayList<>(cart.getItems());
		List<Product> products2 = daoProductRepository.findAll();
		List<Product> products3 = new ArrayList<>();
		double totalAmount = 0.0;
		for (Product p1 : products1) {
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

		return "/views/details";
	}
}
