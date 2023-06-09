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
import com.poly.asm.dao.ProductRepository;
import com.poly.asm.dao.UserRepository;
import com.poly.asm.model.Product;
import com.poly.asm.model.User;
import com.poly.asm.service.SessionService;
import com.poly.asm.service.ShoppingCartService;

import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("/shoeshop")
public class PayController {
	@Autowired
	SessionService session;
	@Autowired
	UserRepository dao; // user
	@Autowired
	ProductRepository Pdao; // sản phẩm

	@Autowired
	private IndexController indexController;

	@Autowired
	private ShoppingCartService cart;//gio hang
	

	@GetMapping("/thanhtoan")
	public String thanhtoan(@ModelAttribute("user") User user, Model model) {
		
		indexController.checkUser(model);
		
		List<Product> products = new ArrayList<>(cart.getItems());
		List<Product> products2 = Pdao.findAll();
		List<Product> products3 = new ArrayList<>();
		 double totalAmount = 0.0;
		for (Product p1 : products) {
			for (Product p2 : products2) {
				if (p1.getId().equalsIgnoreCase(p2.getId())) {
					p2.setQuantity(1);
					products3.add(p2);
					 totalAmount += p2.getPrice();
					 
				}
			}
		}
		
		model.addAttribute("cart", products3);
		
		//tạo biến Tổng ti lưu  tạm trong modal
		model.addAttribute("totalAmount", totalAmount);
		model.addAttribute("name", products);
		return "/views/thanhtoan";
	}
	
	@PostMapping("/create")
	public String createPay(Model model, @ModelAttribute("product") Product product) {

		
		return "/views/thanhtoan";
		
	}

}
