package com.poly.asm.controller.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.poly.asm.config.short_method.Mail;
import com.poly.asm.controller.IndexController;
import com.poly.asm.dao.CategoryRepository;
import com.poly.asm.dao.ProductRepository;
import com.poly.asm.dao.UserRepository;
import com.poly.asm.model.Category;
import com.poly.asm.model.MailInfo2;
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
	@Autowired
	private Mail mail;


//
	@GetMapping("/personal-page")
	public String Personal(@ModelAttribute("user") User user, Model model) {
		indexController.checkUser(model);
//		Giỏ hàng
//		user = session.get("user");
//		model.addAttribute("image", user.getImage());
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

	private String sendCodeString = "";
	public static String generateRandomNumber() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 9; i++) {
			int digit = random.nextInt(10); // Sinh số ngẫu nhiên từ 0 đến 9
			sb.append(digit);
		}

		return sb.toString();
	}
	@PostMapping("/personal-page")
	public String PersonalCheck(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model) {
		
		System.out.println(user.getID());
		System.out.println(user.getName());
		System.out.println(user.getPassword());
		System.out.println(user.getEmail());
		System.out.println(user.getAddress());
		System.out.println(user.getPhone());
		
		indexController.checkUser(model);
		List<User> users = dao.findAll();
		
		if (rs.hasErrors()) {
			System.out.println(rs.toString());
			return "/account/personalpage";
		}


	 // Lấy giá trị ID của người dùng mới
//		if (!user.getPhone().equalsIgnoreCase(user.getPhone())) {
//			String successMessage = "SDT không trùng nhau!";
//			model.addAttribute("failed", successMessage);
//			return "/account/personalpage";
//		}
		String email = user.getEmail();
		for (User user2 : users) {
		    if (user2.getEmail().equals(email)) {
		    	String successMessage = "Email đã tồn tại!";
				model.addAttribute("failed", successMessage);
			}
		}
		
		
		MailInfo2 mailInfo2 = new MailInfo2();
		sendCodeString = generateRandomNumber();

		String body = "<html>" 
                + "<head>" 
                + "<style>" 
                + "body { font-family: Arial, sans-serif; }"
                + ".container { max-width: 600px; margin: 0 auto; padding: 20px; }"
                + ".header { background-color: #87CEEB; padding: 20px; text-align: center; }" 
                + ".header h2 { margin: 0; color: #FFF; }" 
                + ".content { background-color: #FFFFFF; padding: 20px; }"
                + ".content p { color: #333; }"
                + ".content h3 { color: #87CEEB; text-align: center; background-color: #FFFFFF; padding: 10px; font-size: 24px; border: 1px solid #87CEEB; }"
                + ".footer { background-color: #87CEEB; padding: 20px; text-align: center; }"
                + ".footer p { color: #FFF; }" 
                + "</style>" 
                + "</head>" 
                + "<body>" 
                + "<div class='container'>"
                + "<div class='header'>" 
                + "<h2>SHOE SHOP CODE</h2>" 
                + "</div>" 
                + "<div class='content'>"
                + "<p>Email của bạn đã được cập nhật thành công!</p>"
                + "<p>Email mới của bạn là: " + user.getEmail() + "</p>"
                + "</div>" 
                + "<div class='footer'>"
                + "<p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.</p>" 
                + "</div>" 
                + "</div>" 
                + "</body>"
                + "</html>";
		

		mail.setMail(user, body);

		
		model.addAttribute("image", user.getImage());
		user.setPassword(user.getPassword());
		user.setImage(user.getImage());
		dao.save(user);
		indexController.checkUser(model);
		model.addAttribute("message", "cập nhật thành công");
		return "/account/personalpage";
	}
}
