package com.poly.asm.controller.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.asm.controller.IndexController;
import com.poly.asm.dao.DetailedInvoiceRepository;
import com.poly.asm.dao.InvoiceRepository;
import com.poly.asm.dao.ProductRepository;
import com.poly.asm.dao.UserRepository;
import com.poly.asm.service.ShoppingCartService;

@Controller
@RequestMapping("/shoeshop")
public class ViewPayController {
	@Autowired
	UserRepository daoUserRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	private IndexController indexController;

	@Autowired
	private ShoppingCartService cart;// gio hang

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private DetailedInvoiceRepository DetailedInvoiceRepository;

//	@GetMapping("/viewpay")
//	public String viewpay(@ModelAttribute("user") User user, Model model, @ModelAttribute("product") Product product) {
//
//		indexController.checkUser(model);
//
//		List<Product> products = new ArrayList<>(cart.getItems());
//		List<Product> products2 = productRepository.findAll();
//		List<Product> products3 = new ArrayList<>();
//		double totalAmount = 0.0;
//		for (Product p1 : products) {
//			for (Product p2 : products2) {
//				if (p1.getId().equalsIgnoreCase(p2.getId())) {
//					p2.setQuantity(p1.getQuantity());
//					products3.add(p2);
//					totalAmount += p2.getPrice();
//				}
//			}
//		}
//		
//
//		
//		model.addAttribute("cart", products3);
//
//		// tạo biến Tổng ti lưu tạm trong modal
//		model.addAttribute("totalAmount", totalAmount);
//		model.addAttribute("name", products);
//		return "views/viewPay";
//	}

}
