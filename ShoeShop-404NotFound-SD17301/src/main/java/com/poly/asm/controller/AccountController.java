package com.poly.asm.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.asm.dao.UserRepository;
import com.poly.asm.model.User;
import com.poly.asm.service.CookieService;
import com.poly.asm.service.ParamService;
import com.poly.asm.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/shoeshop")
public class AccountController {

	@Autowired
	UserRepository dao; // dao của user

	@Autowired
	CookieService cookieService;

	@Autowired
	ParamService paramService;

	@Autowired
	SessionService sessionService;

	@Autowired
	HttpServletRequest request;

	@Autowired
	SessionService session;

	@RequestMapping("/login")
	public String login(@ModelAttribute("user") User user, Model model) {

		user.setEmail(cookieService.getValue("email"));
		return "/account/login";
	}

	@PostMapping("/login")
	public String loginCheck(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model,
			@RequestParam(name = "remember", defaultValue = "false") boolean remember) {
		List<User> users = dao.findAll();

		if (remember) {
			cookieService.add("email", user.getEmail(), 10);
//			System.out.println(cookieService.getValue("email") + "cookie");
		} else {
			cookieService.remove("email");
		}

		for (User user2 : users) {
			if (user.getEmail().equalsIgnoreCase(user2.getEmail())) {
				if (user.getPassword().equalsIgnoreCase(user2.getPassword())) {
					if (user2.isAdmin()) {
						session.set("user", user2);
						User sUser = session.get("user");
//						System.out.println(sUser.getImage());
						return "redirect:/shoeshop/admin/index";
					} else {
						session.set("user", user2);
						User sUser = session.get("user");
//						System.out.println(sUser.getImage());
						return "redirect:/shoeshop/index";
					}
				}
			}
		}
		String successMessage = "Tài khoản hoặc mật khẩu không chính xác?";
		model.addAttribute("failed", successMessage);
		return "/account/login";
	}

	// trang signUp
	@GetMapping("/signUp")
	public String signUp(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("dk_user", user);

//		List<User> lits_user = dao.findAll();
//		model.addAttribute("lits_user", lits_user);

		user.setPhone("SĐT NUll");
		return "/account/signUp";
	}

	// trang signUp
	public static String generateRandomNumber() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 9; i++) {
			int digit = random.nextInt(10); // Sinh số ngẫu nhiên từ 0 đến 9
			sb.append(digit);
		}

		return sb.toString();
	}

	@PostMapping("/signUp")
	public String signUp(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model,
			@RequestParam("pw1") String pw1) {

		List<User> users = dao.findAll();
		if (rs.hasErrors()) {
			String successMessage = "create failed";
			model.addAttribute("failed", successMessage);
			System.out.println(rs.toString());
			return "/account/signUp";
		}

		for (User b : users) {
			if (b.getID().equalsIgnoreCase(user.getID())) {
				String successMessage = "ID đã tồn tại !";
				model.addAttribute("failed", successMessage);
				return "/account/signUp";
			}
		}
		for (User b : users) {
			if (b.getEmail().equalsIgnoreCase(user.getEmail())) {
				String successMessage = "gmail đã tồn tại !";
				model.addAttribute("failed", successMessage);
				return "/account/signUp";
			}
		}

		if (!user.getPassword().equalsIgnoreCase(pw1)) {
			String successMessage = "Mật khẩu không trùng nhau!";
			model.addAttribute("failed", successMessage);
			return "/account/signUp";
		}
		dao.save(user);

		return "redirect:/shoeshop/login";
	}

	// trang logout
	@RequestMapping("/log-out")
	public String logOut(@ModelAttribute("user") User user) {
		session.remove("user");
		return "redirect:/shoeshop/index";
	}

	// trang kiểm tra password
	@RequestMapping("/ChangePass")
	public String ChangePass(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("ui_user", "active");
		return "/account/ChangePass";
	}

	// trang nhập mật khẩu mới confrimPassword 
	@PostMapping("/ChangePass")
	public String ChangePassCheck(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model) {
//		if (user.getPassword().equalsIgnoreCase("123")) {
//
//			
//			return "redirect:/shoeshop/ChangeRePass";
//		}
		
		if (dao.findAll().get(0).getPassword().equals(user.getPassword())) {
			System.out.println(user.getPassword());
			System.out.println("thành công");
			
			return "redirect:/shoeshop/ChangeRePass-Change";
		}else {
//			System.out.println("mật khẩu sai");
			model.addAttribute("message", "mat khau sai");
		
					}
		return "/account/ChangePass";
	}

	// trang nhập mật khẩu mới
	@GetMapping("/ChangeRePass-Change")
	public String ChangeRePass(@ModelAttribute("user") User user, Model model) {
		
		model.addAttribute("ui_user", "change");		
		return "/account/ChangeRePass";
	}

	//phương thức thay đổi mật khẩu mới
	@PostMapping("/ChangeRePass-Change")
	public String PassCheck(@Valid @ModelAttribute("user") User user, Model model, BindingResult result) {
				
		List<User> uList = dao.findAll();
		if(user.getPassword().equalsIgnoreCase(uList.get(0).getPassword())) {
			user.setPassword(user.getPassword());
			model.addAttribute(uList);
			dao.save(user);
			System.out.println("đổi mật khẩu thành công");
			
		
			
			return "redirect:/shoeshop/index";
		}else {
			model.addAttribute(uList);
			System.out.println("lỗi");
		}
		return "/account/ChangeRePass";
		
	}
	
	// trang nhập mật khẩu mới
	@RequestMapping("/Forget")
	public String Forget(@ModelAttribute("user") User user) {
		return "/account/Forget";
	}

	// trang nhập mật khẩu mới
	@PostMapping("/Forget")
	public String ForgetCheck(@Valid @ModelAttribute("user") User user, BindingResult rs, Model model) {
		if (user.getEmail().equalsIgnoreCase("user@gmail.com")) {

			return "redirect:/shoeshop/ChangeRePass";
		}
		return "/account/Forget";
	}
}
